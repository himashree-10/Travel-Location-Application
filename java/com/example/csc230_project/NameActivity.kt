package com.example.csc230_project


import android.Manifest
import androidx.appcompat.app.AppCompatActivity

import android.widget.TextView
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.location.Geocoder
import android.widget.Toast
import android.content.Intent
import android.location.Location
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.widget.Button
import com.google.android.gms.location.*


class NameActivity : AppCompatActivity() {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var addressResultReceiver: LocationAddressResultReceiver? = null
    private var currentAddTv: TextView? = null
    private var currentLocation: Location? = null
    private var locationCallback: LocationCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
        addressResultReceiver = LocationAddressResultReceiver(Handler())
        currentAddTv = findViewById(R.id.textView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                currentLocation = locationResult.locations[0]
                address
            }
        }

        val googs = findViewById<Button>(R.id.button15)
        googs.setOnClickListener{
            val Intent = Intent(this, MapsActivity::class.java)
            startActivity(Intent)
        }

        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            val locationRequest = LocationRequest()
            locationRequest.interval = 2000
            locationRequest.fastestInterval = 1000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            fusedLocationClient!!.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    private val address: Unit
        private get() {
            if (!Geocoder.isPresent()) {
                Toast.makeText(
                    this@NameActivity, "Can't find current address, ",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            val intent = Intent(this, GetAddressIntentService::class.java)
            intent.putExtra("add_receiver", addressResultReceiver)
            intent.putExtra("add_location", currentLocation)
            startService(intent)
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(
                    this,
                    "Location permission not granted, " + "restart the app if you want the feature",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private inner class LocationAddressResultReceiver(handler: Handler?) :
        ResultReceiver(handler) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            if (resultCode == 0) {
                Log.d("Address", "Location null retrying")
                address
            }
            if (resultCode == 1) {
                Toast.makeText(this@NameActivity, "Address not found, ", Toast.LENGTH_SHORT).show()
            }
            val currentAdd = resultData.getString("address_result")
            showResults(currentAdd)
        }
    }

    private fun showResults(currentAdd: String?) {
        currentAddTv!!.text = currentAdd
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient!!.removeLocationUpdates(locationCallback!!)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 2
    }


}
