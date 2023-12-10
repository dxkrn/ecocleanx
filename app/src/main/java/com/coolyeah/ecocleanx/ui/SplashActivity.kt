package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.coolyeah.ecocleanx.MainActivity
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivitySplashBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = Firebase.auth


//        binding.btnStart.setOnClickListener{
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }

        Handler(Looper.getMainLooper()).postDelayed({
            //Check is user already loged in
            val currentUser = auth.currentUser
            if (currentUser != null) {
                startActivity(Intent(this,BerandaActivity::class.java))
            } else {
                startActivity(Intent(this,Onboarding1Activity::class.java))
            }
            finish()
        },3000)
    }
}