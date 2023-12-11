package com.coolyeah.ecocleanx

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coolyeah.ecocleanx.adapter.ArtikelAdapter
import com.coolyeah.ecocleanx.databinding.FragmentEdukasiBinding
import com.coolyeah.ecocleanx.model.ArtikelModel
import com.coolyeah.ecocleanx.ui.ArtikelActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EdukasiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EdukasiFragment : Fragment() {
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
    private lateinit var binding: FragmentEdukasiBinding

    //RECYCLERVIEW
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<ArtikelModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEdukasiBinding.inflate(inflater, container, false)
        val view = binding.root

        var artikelData = getArtikelData()

        //RECYCLERVIEW ARTIKEL
        recyclerView = binding.rvArtikel
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

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EdukasiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EdukasiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getArtikelData(): List<Map<String, Any>> {
        val sharedPreferences: SharedPreferences =
            requireActivity().getSharedPreferences("localData", Context.MODE_PRIVATE)

        val gson = Gson()
        val json = sharedPreferences.getString("artikelData", "")

        val type = object : TypeToken<List<Map<String, Any>>>() {}.type

        return gson.fromJson(json, type) ?: emptyList<Map<String, Any>>()
    }
}