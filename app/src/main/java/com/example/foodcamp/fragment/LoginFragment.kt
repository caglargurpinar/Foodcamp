package com.example.foodcamp.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.foodcamp.MainActivity
import com.example.foodcamp.R
import com.example.foodcamp.databinding.FragmentLoginBinding
import com.example.foodcamp.viewmodel.LoginFragmentViewModel
import com.example.foodcamp.viewmodel.LoginVMF

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel: LoginFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        binding.loginFragment = this

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : LoginFragmentViewModel by viewModels(){
            LoginVMF(requireActivity().application)
        }
        viewModel = tempViewModel
    }

    fun signUpTextViewClick(v:View){
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        Navigation.findNavController(v).navigate(action)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email:String, password:String){
        if (email.isNotEmpty() && password.isNotEmpty()){
            viewModel.login(email, password)
            val intent = Intent(activity,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        else{
            Toast.makeText(requireContext(),"Lütfen boş alanları doldurunuz.", Toast.LENGTH_SHORT).show()
        }
    }


}