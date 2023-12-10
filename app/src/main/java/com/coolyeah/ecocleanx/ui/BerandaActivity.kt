package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityBerandaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BerandaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerandaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnNotif.setOnClickListener{
//            startActivity(Intent(this,NotifikasiActivity::class.java))
//            finish()
//        }
//
//        binding.bannerLapor.setOnClickListener{
//            startActivity(Intent(this,LaporInputActivity::class.java))
//            finish()
//        }

        val bottomNavigationView =  findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController= findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)

    }
}