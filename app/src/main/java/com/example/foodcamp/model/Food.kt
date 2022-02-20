package com.example.foodcamp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Food(
    @SerializedName("yemek_id") @Expose var yemek_id: Int,
    @SerializedName("yemek_adi") @Expose var yemek_adi: String,
    @SerializedName("yemek_resim_adi") @Expose var yemek_resim_adi: String,
    @SerializedName("yemek_fiyat") @Expose var yemek_fiyat: Int
) : Serializable {
}