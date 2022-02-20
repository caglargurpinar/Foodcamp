package com.example.foodcamp.repo

import android.app.Application
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.foodcamp.model.*
import com.example.foodcamp.retrofit.ApiUtils
import com.example.foodcamp.retrofit.FoodDaoInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodDaoRepository(var application: Application) {
    var foodList: MutableLiveData<List<Food>>
    var cartList: MutableLiveData<List<Order>>
    var firebaseUser: MutableLiveData<FirebaseUser>
    var foodDao : FoodDaoInterface
    var auth: FirebaseAuth
    var db = Firebase.firestore
    init {
        foodList = MutableLiveData()
        cartList = MutableLiveData()
        foodDao = ApiUtils.getFoodDaoInterface()
        auth = Firebase.auth
        firebaseUser = MutableLiveData()
    }

    fun getFoods() : MutableLiveData<List<Food>> {
        return foodList
    }

    fun getUser() : MutableLiveData<FirebaseUser> {
        val currentUser = auth.currentUser
        firebaseUser.value = currentUser
        return firebaseUser
    }

    fun takeAllFoods(){
        foodDao.allFoods().enqueue(object : Callback<FoodResponse>{
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                val list = response.body().foods
                foodList.value = list
            }
            override fun onFailure(call: Call<FoodResponse>?, t: Throwable?) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(name:String, email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor) { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(name).build()
                    )
                    firebaseUser.value = auth.currentUser
                } else {
                    Toast.makeText(application, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

        val user = hashMapOf(
            "name" to name,
            "email" to email
        )
        db.collection("users").add(user)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor) { task ->
                if (task.isSuccessful) {
                    firebaseUser.value = auth.currentUser
                } else {
                    Toast.makeText(application, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun getCurrentUser() : FirebaseUser{
        val currentUser = auth.currentUser
        return currentUser!!

    }

    fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int){
        Firebase.auth.currentUser?.email?.let {
            foodDao.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, it)
                .enqueue(object : Callback<CRUDResponse>{
                override fun onResponse(call: Call<CRUDResponse>?, response: Response<CRUDResponse>?) {}
                override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {}

            })
        }
    }

    fun getCartList(){
        getUser().value?.email?.let {
            foodDao.getCart(it)
                .enqueue(object : Callback<CartResponse>{
                    override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                        val list =response.body().cart
                        cartList.value = list
                    }

                    override fun onFailure(call: Call<CartResponse>?, t: Throwable?) {

                    }

                })
        }
    }

    fun getCart() : MutableLiveData<List<Order>> {
        return cartList
    }

    fun removeFromCart(sepet_yemek_id:Int){
        getUser().value?.email?.let { foodDao.removeFromCart(sepet_yemek_id, it).enqueue(object :Callback<CRUDResponse>{
            override fun onResponse(call: Call<CRUDResponse>?, response: Response<CRUDResponse>?) {
                getCartList()
            }
            override fun onFailure(call: Call<CRUDResponse>?, t: Throwable?) {}
        }) }
    }

    fun searchFood(searchString:String){
        val job = CoroutineScope(Dispatchers.Main).launch {
            foodList.value = getFoods().value?.let { searchInList(it,searchString) }
        }
    }

    fun searchInList(list: List<Food>,searchString: String) : List<Food>{
        var newList : ArrayList<Food> = ArrayList()
        for (food in list){
            if(food.yemek_adi.contains(searchString)){
                newList.add(food)
            }
        }
        return newList
    }

}