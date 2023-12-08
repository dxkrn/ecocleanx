package com.coolyeah.ecocleanx.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var auth: FirebaseAuth
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

        //SIGNIN
        auth = Firebase.auth

    }

    private fun signin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success")
//                    val user = auth.currentUser
//                    updateUI(user)
                    print("Sign in success!")
                    startActivity(Intent(this,BerandaActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show(

                    )
//                    updateUI(null)
                    print("Sign in Failed!")
                }
            }

    }
}