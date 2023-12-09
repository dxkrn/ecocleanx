package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db:FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener{
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }

        //firebase
        auth = Firebase.auth
        db = Firebase.firestore

        binding.btnDaftar.setOnClickListener {
            var name = binding.etName.text.toString()
            var email = binding.etEmail.text.toString()
            var password = binding.etPass.text.toString()
            var cPassword = binding.etCpass.text.toString()

            signup(name,email,password,cPassword)
        }
    }

    fun signup(name: String, email: String, pass: String, cPass: String) {
        val user = hashMapOf(
            "email" to email,
            "name" to name,
            "telp" to "",
            "gender" to "",
            "profile" to ""
        )

        if (name != "" && email != "" && pass != "" && cPass != "") {
            if (pass == cPass) {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            //add user data to firestore
                            db.collection("users").document(email)
                                .set(user)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(
                                        baseContext,
                                        "Sign Up Berhasil! 🤗",
                                        Toast.LENGTH_SHORT,
                                    ).show()

                                    Firebase.auth.signOut()
                                    startActivity(Intent(this, SigninActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        baseContext,
                                        "Sign Up gagal!🥵",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                }


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Sign Up gagal!🥵",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    baseContext,
                    "Password tidak cocok! 😵‍💫",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        } else {
            Toast.makeText(
                baseContext,
                "Isi form dahulu ya! 😓",
                Toast.LENGTH_SHORT,
            ).show()
        }



    }

}