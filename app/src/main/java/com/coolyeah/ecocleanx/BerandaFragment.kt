package com.coolyeah.ecocleanx

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolyeah.ecocleanx.adapter.ArtikelAdapter
import com.coolyeah.ecocleanx.databinding.FragmentBerandaBinding
import com.coolyeah.ecocleanx.model.ArtikelModel
import com.coolyeah.ecocleanx.model.LaporanModel
import com.coolyeah.ecocleanx.ui.ArtikelActivity
import com.coolyeah.ecocleanx.ui.LaporInputActivity
import com.coolyeah.ecocleanx.ui.NotifikasiActivity
import com.google.firebase.auth.FirebaseAuth
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
 * Use the [BerandaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BerandaFragment : Fragment() {
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

    //BINDING
    private lateinit var binding: FragmentBerandaBinding

    //RECYCLERVIEW
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<ArtikelModel>

    //FIREBASE
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val view = binding.root

        db = Firebase.firestore

        //BUTTONS
        binding.btnNotif.setOnClickListener {
            val intent = Intent(activity, NotifikasiActivity::class.java)
            startActivity(intent)
        }

        binding.bannerLapor.setOnClickListener{
            val intent = Intent(activity, LaporInputActivity::class.java)
            startActivity(intent)
        }

        //SET USERNAME ON BERANDA
        var userData = getUserData()
        binding.textName.text =  userData["name"]

        var artikelData = getArtikelData()

        //RECYCLERVIEW ARTIKEL
        recyclerView = binding.rvArtikelRekomendasi
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        dataList = arrayListOf<ArtikelModel>()

        for (artikel in artikelData) {
            val artikelInstance = ArtikelModel.fromMap(artikel)
            dataList.add(artikelInstance)
        }

        recyclerView.adapter = ArtikelAdapter(context, dataList) { selectedItem ->
            val intent = Intent(activity, ArtikelActivity::class.java)
            intent.putExtra("title", selectedItem.title)
            intent.putExtra("content", selectedItem.content)
            startActivity(intent)
        }

        getRiwayatLaporanDataFromFirebase()

        return  view
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BerandaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    fun getUserData(): Map<String, String> {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("userData", "")

        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(json, type) ?: emptyMap()
    }

    fun getArtikelData(): List<Map<String, Any>> {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("artikelData", "")

        val type = object : TypeToken<List<Map<String, Any>>>() {}.type

        return gson.fromJson(json, type) ?: emptyList<Map<String, Any>>()
    }


    fun getRiwayatLaporanDataFromFirebase(){
        var riwayatLaporanData = ArrayList<LaporanModel>()

        //GET USER EMAIL
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        val docRef = db.collection("laporans").whereEqualTo("email", userEmail)
        docRef.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot != null) {
                    for (document in querySnapshot.documents) {
                        riwayatLaporanData.add(LaporanModel.fromMap(document.data!!))
                    }
                    Log.d("DATA1", riwayatLaporanData.toString())

                    saveRiwayatLaporanData(riwayatLaporanData)
                }
            }
    }

    private fun saveRiwayatLaporanData(data: ArrayList<LaporanModel>) {
        val gson = Gson()

        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val json = gson.toJson(data)

        editor.putString("riwayatLaporanData", json)
        editor.apply()
    }

    private fun getRiwayatLaporanData(): ArrayList<LaporanModel> {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("riwayatLaporanData", "")

        val type = object : TypeToken<ArrayList<LaporanModel>>() {}.type

        return gson.fromJson(json, type) ?: ArrayList()
    }


}
