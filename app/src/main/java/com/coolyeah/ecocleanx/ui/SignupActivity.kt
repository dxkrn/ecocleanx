package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener{
            startActivity(Intent(this, SigninActivity::class.java))
            finish()
        }

        auth = Firebase.auth

        binding.btnDaftar.setOnClickListener {
            var name = binding.etName.text.toString()
            var email = binding.etEmail.text.toString()
            var password = binding.etPass.text.toString()
            var cPassword = binding.etCpass.text.toString()

            signup(name,email,password,cPassword)
        }
    }

    fun signup(name: String, email: String, pass: String, cPass: String) {

        if (name != "" && email != "" && pass != "" && cPass != "") {
            if (pass == cPass) {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                baseContext,
                                "Sign Up Berhasil! 🤗",
                                Toast.LENGTH_SHORT,
                            ).show()

                            Firebase.auth.signOut()
                            startActivity(Intent(this, SigninActivity::class.java))
                            finish()
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