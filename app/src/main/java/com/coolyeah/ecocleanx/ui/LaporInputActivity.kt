package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityLaporInputBinding

class LaporInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaporInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, BerandaActivity::class.java))
            finish()
        }

        binding.btnKirim.setOnClickListener {
            startActivity(Intent(this,LaporSuccessActivity::class.java))
            finish()
        }
    }

}