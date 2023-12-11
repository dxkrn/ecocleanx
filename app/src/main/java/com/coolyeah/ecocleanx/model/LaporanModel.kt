package com.coolyeah.ecocleanx.model


import android.os.Parcel
import android.os.Parcelable

data class LaporanModel(var alamat:String, var date:String, var email:String, var img:String, var keterangan:String, var latlong:String, var lokasi:String, var status:String) {
    companion object {
        fun fromMap(map: Map<String, Any>): LaporanModel {
            val alamat = map["alamat"] as? String ?: throw IllegalArgumentException("Missing or invalid 'id'")
            val date = map["date"] as? String ?: throw IllegalArgumentException("Missing or invalid 'date'")
            val email = map["email"] as? String ?: throw IllegalArgumentException("Missing or invalid 'email'")
            val img = map["img"] as? String ?: throw IllegalArgumentException("Missing or invalid 'img'")
            val keterangan = map["keterangan"] as? String ?: throw IllegalArgumentException("Missing or invalid 'keterangan'")
            val latlong = map["latlong"] as? String ?: throw IllegalArgumentException("Missing or invalid 'latlong'")
            val lokasi = map["lokasi"] as? String ?: throw IllegalArgumentException("Missing or invalid 'lokasi'")
            val status = map["status"] as? String ?: throw IllegalArgumentException("Missing or invalid 'status'")


            return LaporanModel(alamat, date, email, img, keterangan, latlong, lokasi, status)
        }
    }
}
