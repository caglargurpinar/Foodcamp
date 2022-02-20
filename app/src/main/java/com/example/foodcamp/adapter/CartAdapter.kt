package com.example.foodcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcamp.databinding.CartFoodRowBinding
import com.example.foodcamp.databinding.FoodCardRowBinding
import com.example.foodcamp.model.Food
import com.example.foodcamp.model.Order
import com.example.foodcamp.retrofit.ApiUtils
import com.example.foodcamp.viewmodel.CartFragmentViewModel
import com.squareup.picasso.Picasso

class CartAdapter(var context : Context,
                  var foodList: List<Order>,
                  var viewModel: CartFragmentViewModel) : RecyclerView.Adapter<CartAdapter.CardViewHolder>() {
    inner class CardViewHolder(cardRowBinding: CartFoodRowBinding) :
        RecyclerView.ViewHolder(cardRowBinding.root) {
        var cardRowBinding: CartFoodRowBinding

        init {
            this.cardRowBinding = cardRowBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = CartFoodRowBinding.inflate(layoutInflater,parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val food = foodList.get(position)
        val t = holder.cardRowBinding
        t.foodObject = food
        Picasso.get()
            .load("${ApiUtils.BASE_URL}yemekler/resimler/${food.yemek_resim_adi}")
            .into(t.imageViewFoodCard)

        t.imageViewRemove.setOnClickListener {
            viewModel.removeFromCart(food.sepet_yemek_id)
        }

    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}