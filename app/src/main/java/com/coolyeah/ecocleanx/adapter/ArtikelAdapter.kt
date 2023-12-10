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

class ArtikelAdapter(private val context: Context?, private val dataList:ArrayList<ArtikelModel>):RecyclerView.Adapter<ArtikelAdapter.ViewHolderClass>() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
    val itemView= LayoutInflater.from(parent.context).inflate(R.layout.card_artikel, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.rvTitle.text = currentItem.title
//        holder.rvImage.setImageResource(currentItem.img)
        holder.rvDate.text = currentItem.date
        Glide.with(context!!)
            .load(currentItem.img)
            .into(holder.rvImage)
    }

    class ViewHolderClass(itemView: View): RecyclerView.ViewHolder(itemView){
        val rvTitle:TextView = itemView.findViewById(R.id.rv_artikel_title)
        val rvImage:ImageView = itemView.findViewById(R.id.rv_artikel_img)
        val rvDate:TextView = itemView.findViewById(R.id.rv_artikel_date)

    }
}