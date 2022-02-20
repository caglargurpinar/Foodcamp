package com.example.foodcamp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.foodcamp.AuthActivity
import com.example.foodcamp.MainActivity
import com.example.foodcamp.R
import com.example.foodcamp.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profileFragment = this

        binding.textViewProfileName.text = Firebase.auth.currentUser?.displayName
        binding.textViewProfileEmail.text = Firebase.auth.currentUser?.email
        binding.buttonPrevOrders.setOnClickListener {
            Navigation.findNavController(it).navigate(ProfileFragmentDirections.actionProfileFragmentToOldOrdersFragment())
        }
        return binding.root
    }

    fun quitOnClick(){
        Firebase.auth.signOut()
        val intent = Intent(activity,AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}