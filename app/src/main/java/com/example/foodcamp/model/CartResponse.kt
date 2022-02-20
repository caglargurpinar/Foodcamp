package com.example.foodcamp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartResponse(@SerializedName("sepet_yemekler") @Expose var cart:List<Order>,
                   @SerializedName("success") @Expose var success:Int) {
}