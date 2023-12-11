package com.coolyeah.ecocleanx.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.model.ArtikelModel
import com.coolyeah.ecocleanx.model.LaporanModel

class LaporanAdapter(private val context: Context?, private val dataList:ArrayList<LaporanModel>, private val onItemClick: (LaporanModel) -> Unit):RecyclerView.Adapter<LaporanAdapter.ViewHolderClass>() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
    val itemView= LayoutInflater.from(parent.context).inflate(R.layout.card_riwayat_lapor, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.rvLokasi.text = currentItem.lokasi
        holder.rvDate.text = currentItem.date
        holder.rvStatus.text = currentItem.status

        holder.itemView.setOnClickListener {
            // Call the onItemClick function and pass the clicked item data
            onItemClick(currentItem)
        }
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvLokasi:TextView = itemView.findViewById(R.id.rv_lapor_lokasi)
        val rvDate:TextView = itemView.findViewById(R.id.rv_lapor_date)
        val rvStatus:TextView = itemView.findViewById(R.id.rv_lapor_status)


    }
}