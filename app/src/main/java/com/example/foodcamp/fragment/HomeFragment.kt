package com.example.foodcamp.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.foodcamp.model.Food
import com.example.foodcamp.R
import com.example.foodcamp.adapter.FoodAdapter
import com.example.foodcamp.databinding.FragmentHomeBinding
import com.example.foodcamp.viewmodel.HomeFragmentViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var adapter: FoodAdapter
    private lateinit var foodList: List<Food>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.homeFragment = this

        viewModel.foodList.observe(viewLifecycleOwner,{
            adapter = FoodAdapter(requireContext(),it,viewModel)
            binding.foodAdapter = adapter
        })
        //foodList = viewModel.foodList.value!!
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun addFoodToCart(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int){
        viewModel.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, 1)
    }


}