package com.coolyeah.ecocleanx.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }

        binding.btnMasuk.setOnClickListener{
            var email: String = binding.etEmail.text.toString()
            var password: String = binding.etPass.text.toString()

            signin(email,password)
        }

        //FIREBASE
        auth = Firebase.auth
        db = Firebase.firestore

    }

    private fun signin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    getArtikelData()

                    val docRef = db.collection("users").document(email)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                var data = document.data
//                                Log.d("DATA", data)

                                //convert to string using gson
                                val gson = Gson()
                                val jsonData = gson.toJson(data)

                                //save to local
                                saveUserData(jsonData)

                                startActivity(Intent(this,BerandaActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Sign in failed!😓",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                baseContext,
                                "Sign in failed!😓",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                } else {
                    Toast.makeText(
                        baseContext,
                        "Sign in failed!😓",
                        Toast.LENGTH_SHORT,
                    ).show()
                    print("Sign in Failed!")
                }
            }

    }

    private fun saveUserData(data: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("localData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("userData", data)
        editor.apply()
    }

    private fun getUserData(): Map<String, String> {
       val sharedPreferences: SharedPreferences =
        getSharedPreferences("localData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("userData", "")

        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(json, type) ?: emptyMap()
    }

    fun getArtikelData() {
        val artikelData =  mutableListOf<Map<String, Any>>()

        val docRef = db.collection("materials")
        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {


                    artikelData.add(document.data)

                }
                //convert to string using gson
                val gson = Gson()
                val jsonData = gson.toJson(artikelData)

                //save to local
                saveArtikelData(jsonData)
            }
            .addOnFailureListener { exception ->
                Log.e("ARTIKEL", exception.toString() )
            }
    }

    private fun saveArtikelData(data: String) {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("localData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("artikelData", data)
        editor.apply()
    }



}