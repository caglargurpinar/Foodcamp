package com.example.foodcamp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.foodcamp.repo.FoodDaoRepository
import com.google.firebase.auth.FirebaseUser

class DetailFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val repo = FoodDaoRepository(application)
    var firebaseUser = MutableLiveData<FirebaseUser>()


    fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int){
        repo.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet)
    }

    fun getUser() : MutableLiveData<FirebaseUser> {
        return repo.getUser()
    }

    fun getCurrentUser() : FirebaseUser {
        return repo.getCurrentUser()
    }
}