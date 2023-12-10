package com.coolyeah.ecocleanx.model


import android.os.Parcel
import android.os.Parcelable

data class ArtikelModel(var id:String, var title:String, var date: String, var img: String, var content:String) {
    companion object {
        fun fromMap(map: Map<String, Any>): ArtikelModel {
            val id = map["id"] as? String ?: throw IllegalArgumentException("Missing or invalid 'id'")
            val title = map["title"] as? String ?: throw IllegalArgumentException("Missing or invalid 'title'")
            val date = map["date"] as? String ?: throw IllegalArgumentException("Missing or invalid 'date'")
            val img = map["img"] as? String ?: throw IllegalArgumentException("Missing or invalid 'img'")
            val content = map["content"] as? String ?: throw IllegalArgumentException("Missing or invalid 'content'")


            return ArtikelModel(id, title,date,img,content)
        }
    }
}
//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readInt()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(dataImage)
//        parcel.writeString(dataTitle)
//        parcel.writeString(dataDesc)
//        parcel.writeInt(dataDetailImage)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ArtikelModel> {
//        override fun createFromParcel(parcel: Parcel): ArtikelModel {
//            return ArtikelModel(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ArtikelModel?> {
//            return arrayOfNulls(size)
//        }
//    }
//}