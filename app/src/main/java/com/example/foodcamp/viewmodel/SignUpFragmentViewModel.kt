package com.example.foodcamp.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import com.example.foodcamp.repo.FoodDaoRepository

class SignUpFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val repo = FoodDaoRepository(application)

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(name:String, email:String, password:String){
        repo.register(name, email, password)
    }
}