package com.coolyeah.ecocleanx.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


        val bottomNavigationView =  findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController= findNavController(R.id.fragment)

        bottomNavigationView.setupWithNavController(navController)

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