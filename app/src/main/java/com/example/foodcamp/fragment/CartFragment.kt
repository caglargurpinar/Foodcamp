package com.example.foodcamp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.foodcamp.R
import com.example.foodcamp.adapter.CartAdapter
import com.example.foodcamp.databinding.FragmentCartBinding
import com.example.foodcamp.model.Order
import com.example.foodcamp.viewmodel.CartFragmentViewModel
import com.example.foodcamp.viewmodel.CartVMF
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var adapter: CartAdapter
    private lateinit var viewModel: CartFragmentViewModel
    private lateinit var user: FirebaseUser
    var list = ArrayList<Order>()
    var db = Firebase.firestore
    val auth = Firebase.auth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        user = viewModel.getCurrentUser()
        viewModel.getFoods()
        viewModel.getUser().observe(viewLifecycleOwner,{
            user = it
        })
        binding.cartFragment = this
        viewModel.cartList.observe(viewLifecycleOwner,{
            if(it!=null){
                adapter = CartAdapter(requireContext(),it,viewModel)
                adapter.notifyDataSetChanged()
                binding.cartAdapter = adapter
                list = it as ArrayList<Order>
                var totalCost=0
                for (order in list){
                    totalCost += order.yemek_siparis_adet*order.yemek_fiyat
                    order.date = currentDate
                }
                binding.textViewCartTotal.text = "${totalCost} ₺"
            }

        })


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : CartFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }
    var orderList = ArrayList<ArrayList<Order>>()
    fun btnOrderOnClick(v:View){
        for (order in list){
            viewModel.removeFromCart(order.sepet_yemek_id)
        }

        db.collection("users").document(auth.currentUser?.email!!).collection("orders").document().set(hashMapOf("order" to list))//.add(hashMapOf("order" to list))
        val action = CartFragmentDirections.actionCartFragmentToHomeFragment()
        Navigation.findNavController(v).navigate(action)
        Toast.makeText(requireContext(),"Sipariş alındı",Toast.LENGTH_SHORT).show()
    }

}