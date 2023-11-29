package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityBerandaBinding

class BerandaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerandaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotif.setOnClickListener{
            startActivity(Intent(this,NotifikasiActivity::class.java))
            finish()
        }

    }
}