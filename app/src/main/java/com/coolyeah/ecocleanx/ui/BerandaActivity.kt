package com.coolyeah.ecocleanx.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityBerandaBinding
import com.coolyeah.ecocleanx.databinding.FragmentBerandaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class BerandaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerandaBinding
    private lateinit var userData: Map<String, String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nav()

    }


    private fun nav(){
        val bottomNavigationView =  findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val colorStateList = ContextCompat.getColorStateList(this, R.color.btn_nav_icon_color)
        val navController= findNavController(R.id.fragment)

        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.berandaFragment -> {
                    // Handle Home item click
                    bottomNavigationView.setupWithNavController(navController)
                    val selectedColor = ContextCompat.getColor(this, R.color.primary)
                    // Change icon color if needed
                    item.icon?.setTint(selectedColor)
                }
                R.id.edukasiFragment -> {
                    bottomNavigationView.setupWithNavController(navController)
                    val selectedColor = ContextCompat.getColor(this, R.color.primary)
                    // Change icon color if needed
                    item.icon?.setTint(selectedColor)
                }
                R.id.pelaporanFragment -> {
                    bottomNavigationView.setupWithNavController(navController)
                    val selectedColor = ContextCompat.getColor(this, R.color.primary)
                    // Change icon color if needed
                    item.icon?.setTint(selectedColor)
                }
                R.id.profileFragment -> {
                    bottomNavigationView.setupWithNavController(navController)
                    val selectedColor = ContextCompat.getColor(this, R.color.primary)
                    // Change icon color if needed
                    item.icon?.setTint(selectedColor)
                }
            }
            true // Return true to indicate the item selection is handled
        }
    }

//    fun getUserData(): Map<String, String> {
//        val sharedPreferences: SharedPreferences =
//            getSharedPreferences("localData", Context.MODE_PRIVATE)
//
//        val gson = Gson()
//        val json = sharedPreferences.getString("userData", "")
//
//        val type = object : TypeToken<Map<String, String>>() {}.type
//        return gson.fromJson(json, type) ?: emptyMap()
//    }
}