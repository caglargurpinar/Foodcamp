package com.example.foodcamp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.foodcamp.model.Food
import com.example.foodcamp.model.Order
import com.example.foodcamp.repo.FoodDaoRepository
import com.google.firebase.auth.FirebaseUser

class CartFragmentViewModel(application: Application) : AndroidViewModel(application) {
    var cartList = MutableLiveData<List<Order>>()
    val repo = FoodDaoRepository(application)

    init {
        getFoods()
        cartList = repo.getCart()
    }

    fun getFoods(){
        repo.getCartList()
    }

    fun getUser() : MutableLiveData<FirebaseUser> {
        return repo.getUser()
    }

    fun getCurrentUser() : FirebaseUser {
        return repo.getCurrentUser()
    }

    fun removeFromCart(sepet_yemek_id:Int){
        repo.removeFromCart(sepet_yemek_id)
    }
}