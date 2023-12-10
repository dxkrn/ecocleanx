package com.coolyeah.ecocleanx

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.coolyeah.ecocleanx.databinding.FragmentBerandaBinding
import com.coolyeah.ecocleanx.databinding.FragmentProfileBinding
import com.coolyeah.ecocleanx.ui.BerandaActivity
import com.coolyeah.ecocleanx.ui.NotifikasiActivity
import com.coolyeah.ecocleanx.ui.SigninActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var binding: FragmentProfileBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        db = Firebase.firestore


        //BUTTONS
        binding.btnKeluar.setOnClickListener {
            signout()
            val intent = Intent(activity, SigninActivity::class.java)
            startActivity(intent)
        }

        //SET USER DATA ON PROFILE
        var userData = getUserData()
        binding.etNama.setText(userData["name"])
        binding.etTelp.setText(userData["telp"])
        if (userData["gender"] == "Laki-laki") {
            binding.L.isChecked = true
        }
        if (userData["gender"] == "Perempuan") {
            binding.P.isChecked = true
        }

        //UPDATE PROFILE
        binding.btnPerbarui.setOnClickListener {
            var name = binding.etNama.text.toString()
            var telp = binding.etTelp.text.toString()
            var  gender = ""

            if (binding.L.isChecked) {
                gender = "Laki-laki"
            }

            if (binding.P.isChecked) {
                gender = "Perempuan"
            }

            updateProfile(userData["email"].toString(), name, telp, gender)
        }


        return  view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun signout() {
        Firebase.auth.signOut()
    }

    fun getUserData(): Map<String, String> {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("userData", "")

        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(json, type) ?: emptyMap()
    }

    fun updateProfile(email:String, name:String, telp:String, gender:String) {
        val user = hashMapOf(
            "email" to email,
            "name" to name,
            "telp" to telp,
            "gender" to gender,
            "profile" to ""
        )

        db.collection("users").document(email)
            .set(user)
            .addOnSuccessListener { documentReference ->
                val docRef = db.collection("users").document(email)
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            var data = document.data

                            //convert to string using gson
                            val gson = Gson()
                            val jsonData = gson.toJson(data)

                            //save to local
                            saveUserData(jsonData)

                            Toast.makeText(
                                activity,
                                "Update Profile Berhasil! 🤗",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(activity, BerandaActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                activity,
                                "Update Profile gagal!😓",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(
                            activity,
                            "Update Profile failed!😓",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    activity,
                    "Update Profile gagal!🥵",
                    Toast.LENGTH_SHORT,
                ).show()
            }
    }
    private fun saveUserData(data: String) {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("userData", data)
        editor.apply()
    }
}

