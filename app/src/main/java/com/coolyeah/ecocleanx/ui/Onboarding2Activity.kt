package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityOnboarding2Binding

class Onboarding2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboarding2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSebelumnya.setOnClickListener{
            startActivity(Intent(this,Onboarding1Activity::class.java))
            finish()
        }

        binding.btnSelanjutnya.setOnClickListener{
            startActivity(Intent(this,Onboarding3Activity::class.java))
        }

        binding.btnSkip.setOnClickListener{
            startActivity(Intent(this,OnboardingEndActivity::class.java))
            finish()
        }
    }
}