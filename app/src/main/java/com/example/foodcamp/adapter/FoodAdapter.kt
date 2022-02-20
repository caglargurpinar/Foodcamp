package com.example.foodcamp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcamp.databinding.FoodCardRowBinding
import com.example.foodcamp.fragment.HomeFragmentDirections
import com.example.foodcamp.model.Food
import com.example.foodcamp.retrofit.ApiUtils
import com.example.foodcamp.viewmodel.HomeFragmentViewModel
import com.squareup.picasso.Picasso

class FoodAdapter(
    var context : Context,
    var foodList: List<Food>,
    var viewModel: HomeFragmentViewModel) : RecyclerView.Adapter<FoodAdapter.CardViewHolder>() {
    inner class CardViewHolder(cardRowBinding: FoodCardRowBinding) : RecyclerView.ViewHolder(cardRowBinding.root) {
        var cardRowBinding: FoodCardRowBinding

        init {
            this.cardRowBinding = cardRowBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = FoodCardRowBinding.inflate(layoutInflater,parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val food = foodList.get(position)
        val t = holder.cardRowBinding
        t.foodObject = food
        Picasso.get()
            .load("${ApiUtils.BASE_URL}yemekler/resimler/${food.yemek_resim_adi}")
            .into(t.imageViewFoodCard)
        t.card.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(food)
            Navigation.findNavController(it).navigate(action)
        }
        t.fabAddCart.setOnClickListener {
            viewModel.addFoodToCart(food.yemek_adi,food.yemek_resim_adi,food.yemek_fiyat,1)
        }
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}