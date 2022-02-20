package com.example.foodcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodcamp.databinding.FoodCardRowBinding
import com.example.foodcamp.databinding.OldOrdersCardBinding
import com.example.foodcamp.model.Food
import com.example.foodcamp.model.OldOrders
import com.example.foodcamp.model.Order

class OldOrdersAdapter(var context : Context,
                       var orderList: List<OldOrders>) : RecyclerView.Adapter<OldOrdersAdapter.CardViewHolder>() {
    inner class CardViewHolder(cardRowBinding: OldOrdersCardBinding) : RecyclerView.ViewHolder(cardRowBinding.root) {
        var cardRowBinding: OldOrdersCardBinding

        init {
            this.cardRowBinding = cardRowBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = OldOrdersCardBinding.inflate(layoutInflater,parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val oldOrder = orderList.get(position)
        val t = holder.cardRowBinding
        t.orderObject = oldOrder
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}