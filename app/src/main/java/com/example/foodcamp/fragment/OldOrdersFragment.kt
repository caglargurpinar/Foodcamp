package com.example.foodcamp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.room.Index
import com.example.foodcamp.R
import com.example.foodcamp.adapter.OldOrdersAdapter
import com.example.foodcamp.databinding.FragmentOldOrdersBinding
import com.example.foodcamp.model.OldOrders
import com.example.foodcamp.model.Order
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OldOrdersFragment : Fragment() {
    private lateinit var binding: FragmentOldOrdersBinding
    val db = Firebase.firestore
    val auth = Firebase.auth
    var oldList = ArrayList<OldOrders>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_old_orders, container, false)

        binding.oldOrdersFragment = this

        var currentDate = ""
        val docRef = db.collection("users").document(auth.currentUser?.email!!).collection("orders")

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    for (i in document.documents){
                        var name = ""
                        var price: Long = 0
                        var list = i.data?.get("order") as ArrayList<HashMap<String,String>>
                        for(item in list){
                            name = name.plus("${item.get("yemek_adi")} ")
                            price += item.get("yemek_fiyat") as Long
                            currentDate = item.get("date")!!
                        }
                        var priceInt = price.toString()
                        name = name.trim().replace(" ","  -  ")
                        oldList.add(OldOrders(name,priceInt.toInt(),currentDate))
                    }
                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
            .addOnCompleteListener{
                val adapter = OldOrdersAdapter(requireContext(),oldList)
                binding.oldOrdersAdapter = adapter
            }

        return binding.root
    }

    fun backPressed(){
        activity?.onBackPressed()
    }

}