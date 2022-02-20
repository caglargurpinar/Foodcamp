package com.example.foodcamp.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.foodcamp.R
import com.example.foodcamp.databinding.FragmentSignUpBinding
import com.example.foodcamp.viewmodel.SignUpFragmentViewModel
import com.example.foodcamp.viewmodel.SignUpVMF

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.signUpFragment = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : SignUpFragmentViewModel by viewModels(){
            SignUpVMF(requireActivity().application)
        }
        viewModel = tempViewModel
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(name:String, email:String, password:String, v:View){
        if (email.isNotEmpty() && password.isNotEmpty()){
            viewModel.register(name, email, password)
            Navigation.findNavController(v).navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
        }
        else{
            Toast.makeText(requireContext(),"Lütfen boş alanları doldurunuz.",Toast.LENGTH_SHORT).show()
        }
    }

}