package com.coolyeah.ecocleanx.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.coolyeah.ecocleanx.R
import com.coolyeah.ecocleanx.databinding.ActivityLaporInputBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LaporInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaporInputBinding

    //GPS
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2

    //CAMERA
    val REQUEST_IMAGE_CAPTURE = 1

    //FIREBASE STORAGE
    private  lateinit var storage: FirebaseStorage

    //FIREBASE FIRESTORE
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaporInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //GPS
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Firebase Storage
        storage = Firebase.storage

        //Firebase Firestore
        db = Firebase.firestore


        //Buttons
        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, BerandaActivity::class.java))
            finish()
        }

        binding.btnKirim.setOnClickListener {
            sendLaporan()
//            startActivity(Intent(this,LaporSuccessActivity::class.java))
//            finish()
        }

        binding.btnAddPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        //get location on create
        getLocation()

    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: MutableList<Address>? =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                        mainBinding.apply {
//                            tvLatitude.text = "Latitude\n${list[0].latitude}"
//                            tvLongitude.text = "Longitude\n${list[0].longitude}"
//                            tvCountryName.text = "Country Name\n${list[0].countryName}"
//                            tvLocality.text = "Locality\n${list[0].locality}"
//                            tvAddress.text = "Address\n${list[0].getAddressLine(0)}"
//                        }
                        binding.textLatlong.text = "(Lat: " + list!![0].latitude + "\nLong: " + list!![0].longitude + ")"
                        binding.textAddress.text = list!![0].getAddressLine(0)
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }


    //CAPTURE IMAGE FROM CAMERA
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.btnAddPhoto.setImageBitmap(imageBitmap)
        }
    }

    //UPLOAD IMAGE
    fun sendLaporan() {
        //LAPORAN DATA
        var lokasi = binding.etLokasi.text.toString()
        var keterangan = binding.etKeterangan.text.toString()
        var alamat = binding.textAddress.text.toString()
        var latLong = binding.textLatlong.text.toString()

        //GET CURRENT DATE
        val currDate = Date()
        val formatter = SimpleDateFormat("yyyyMMddHHmmss")
        val formattedDateTime = formatter.format(currDate)

        //GET USER EMAIL
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        //GENERATE LAPORAN DOC ID
        val laporanID = userEmail+formattedDateTime

        // Create a storage reference from our app
        val storageRef = storage.reference

        // Create a reference to 'laporan/..'
        val laporanImagesRef = storageRef.child("laporan/${userEmail}-${formattedDateTime}.jpg")

        // Get the data from an ImageView as bytes
        val imageView: ImageView = findViewById(R.id.btn_add_photo)
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()

        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uploadTask = laporanImagesRef.putBytes(data)

        uploadTask.addOnFailureListener {
            Toast.makeText(
                baseContext,
                "Kirim Laporan Gagal!😓",
                Toast.LENGTH_SHORT,
            ).show()
        }.addOnSuccessListener { taskSnapshot ->
            laporanImagesRef.downloadUrl.addOnSuccessListener { uri ->
                val imgUrl = uri.toString()
//                Log.d("URL IMG", imgUrl)

                val laporanData = hashMapOf(
                    "email" to userEmail,
                    "lokasi" to lokasi,
                    "keterangan" to keterangan,
                    "alamat" to alamat,
                    "latlong" to latLong,
                    "img" to imgUrl,
                    "date" to currDate
                )

                //add user data to firestore
                db.collection("laporans").document(laporanID)
                    .set(laporanData)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(
                            baseContext,
                            "Kirim Laporan Berhasil! 🤗",
                            Toast.LENGTH_SHORT,
                        ).show()

                        startActivity(Intent(this,LaporSuccessActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            baseContext,
                            "Sign Up gagal!🥵",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }


            }
        }
    }

}