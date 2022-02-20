package com.example.foodcamp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(4000)
            val user = Firebase.auth.currentUser
            if (user!=null){
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
            }
            else{
                val intent = Intent(this@SplashActivity,AuthActivity::class.java)
                startActivity(intent)
            }

            finish()
        }

    }
}