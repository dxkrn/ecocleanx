package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityNotifikasiBinding

class NotifikasiActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotifikasiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, BerandaActivity::class.java))
            finish()
        }
    }
}