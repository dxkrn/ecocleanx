package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityOnboarding1Binding
import com.coolyeah.ecocleanx.databinding.ActivitySplashBinding

class Onboarding1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboarding1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelanjutnya.setOnClickListener{
            startActivity(Intent(this, Onboarding2Activity::class.java))
            finish()
        }

        binding.btnSkip.setOnClickListener{
            startActivity(Intent(this,OnboardingEndActivity::class.java))
            finish()
        }


    }
}