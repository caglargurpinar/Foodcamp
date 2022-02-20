package com.example.foodcamp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.foodcamp.R
import com.example.foodcamp.databinding.FragmentDetailBinding
import com.example.foodcamp.retrofit.ApiUtils
import com.example.foodcamp.viewmodel.DetailFragmentViewModel
import com.example.foodcamp.viewmodel.HomeFragmentViewModel
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailFragmentViewModel
    private lateinit var user : FirebaseUser
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        user = viewModel.getCurrentUser()
        viewModel.getUser().observe(viewLifecycleOwner,{
            user = it
        })
        binding.userObject = user
        binding.detailFragment = this
        val bundle:DetailFragmentArgs by navArgs()
        binding.foodObject = bundle.food
        Picasso.get()
            .load("${ApiUtils.BASE_URL}yemekler/resimler/${bundle.food.yemek_resim_adi}")
            .resize(512,512)
            .into(binding.imageView)


        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int){
        viewModel.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet)
    }

    fun backPressed(){
        activity?.onBackPressed()
    }

}