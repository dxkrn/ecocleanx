package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityArtikelBinding

class ArtikelActivity : AppCompatActivity() {

    lateinit var binding: ActivityArtikelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        binding.textTitle.text = title
        binding.textContent.text = content

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, BerandaActivity::class.java))
            finish()
        }

    }
}