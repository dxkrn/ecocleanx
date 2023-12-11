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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolyeah.ecocleanx.adapter.ArtikelAdapter
import com.coolyeah.ecocleanx.adapter.LaporanAdapter
import com.coolyeah.ecocleanx.databinding.FragmentEdukasiBinding
import com.coolyeah.ecocleanx.databinding.FragmentPelaporanBinding
import com.coolyeah.ecocleanx.model.ArtikelModel
import com.coolyeah.ecocleanx.model.LaporanModel
import com.coolyeah.ecocleanx.ui.ArtikelActivity
import com.coolyeah.ecocleanx.ui.BerandaActivity
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
 * Use the [PelaporanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PelaporanFragment : Fragment() {
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
    private lateinit var binding: FragmentPelaporanBinding

    //RECYCLERVIEW
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPelaporanBinding.inflate(inflater, container, false)
        val view = binding.root


        var riwayatLaporanData = getRiwayatLaporanData()


        //RECYCLERVIEW LAPORAN
        recyclerView = binding.rvRiwayatLaporan
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = LaporanAdapter(context, riwayatLaporanData) { selectedItem ->
            val intent = Intent(activity, ArtikelActivity::class.java)
//            intent.putExtra("title", selectedItem.title)
//            intent.putExtra("content", selectedItem.content)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        Log.d("DATA2", riwayatLaporanData.toString())

        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PelaporanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PelaporanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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