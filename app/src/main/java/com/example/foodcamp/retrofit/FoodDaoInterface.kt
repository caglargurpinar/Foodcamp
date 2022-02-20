package com.example.foodcamp.retrofit

import com.example.foodcamp.model.CRUDResponse
import com.example.foodcamp.model.CartResponse
import com.example.foodcamp.model.FoodResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodDaoInterface {
    @GET("yemekler/tumYemekleriGetir.php")
    fun allFoods() : Call<FoodResponse>

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    fun addFoodToCart(@Field("yemek_adi") yemek_adi:String,
                      @Field("yemek_resim_adi") yemek_resim_adi:String,
                      @Field("yemek_fiyat") yemek_fiyat:Int,
                      @Field("yemek_siparis_adet") yemek_siparis_adet:Int,
                      @Field("kullanici_adi") kullanici_adi:String) : Call<CRUDResponse>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun getCart(@Field("kullanici_adi") kullanici_adi: String) : Call<CartResponse>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun removeFromCart(@Field("sepet_yemek_id") sepet_yemek_id: Int,
                       @Field("kullanici_adi") kullanici_adi: String) : Call<CRUDResponse>
}