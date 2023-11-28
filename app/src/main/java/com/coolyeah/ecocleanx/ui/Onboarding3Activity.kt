package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityOnboarding3Binding

class Onboarding3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboarding3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSebelumnya.setOnClickListener{
            startActivity(Intent(this, Onboarding2Activity::class.java))
            finish()
        }

        binding.btnSelanjutnya.setOnClickListener{
            startActivity(Intent(this, Onboarding4Activity::class.java))
            finish()
        }

        binding.btnSkip.setOnClickListener{
            startActivity(Intent(this,OnboardingEndActivity::class.java))
            finish()
        }
    }
}