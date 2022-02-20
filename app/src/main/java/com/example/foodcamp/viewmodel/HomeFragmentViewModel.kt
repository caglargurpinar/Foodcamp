package com.example.foodcamp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodcamp.model.Food
import com.example.foodcamp.repo.FoodDaoRepository

class HomeFragmentViewModel(application: Application) : AndroidViewModel(application) {
    var foodList = MutableLiveData<List<Food>>()
    val repo = FoodDaoRepository(application)

    init {
        getFoods()
        foodList = repo.getFoods()
    }

    fun getFoods(){
        repo.takeAllFoods()
    }

    fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int){
        repo.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet)
    }
}