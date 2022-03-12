package com.example.csc230_project

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class ACTIVITY_MAPS : FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private var mMap: GoogleMap? = null
    var latitude = 0.0
    var longitude = 0.0
    private val PROXIMITY_RADIUS = 10000
    var mGoogleApiClient: GoogleApiClient? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mLocationRequest: LocationRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps2)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available")
            finish()
        } else {
            Log.d("onCreate", "Google Play Services available.")
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    private fun CheckGooglePlayServices(): Boolean {
        val googleAPI = GoogleApiAvailability.getInstance()
        val result = googleAPI.isGooglePlayServicesAvailable(this)
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(
                    this, result,
                    0
                )!!.show()
            }
            return false
        }
        return true
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                buildGoogleApiClient()
                mMap!!.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
        val btnRestaurant = findViewById<View>(R.id.btnRestaurant) as Button
        btnRestaurant.setOnClickListener(object : View.OnClickListener {
            var Restaurant = "restaurant"
            override fun onClick(v: View) {
                Log.d("onClick", "Button is Clicked")
                mMap!!.clear()
                val url = getUrl(latitude, longitude, Restaurant)
                val DataTransfer = arrayOfNulls<Any>(2)
                DataTransfer[0] = mMap
                DataTransfer[1] = url
                Log.d("onClick", url)
                val getNearbyPlacesData = GetNearbyPlacesData()
                getNearbyPlacesData.execute(*DataTransfer)
                Toast.makeText(this@ACTIVITY_MAPS, "Nearby Restaurants", Toast.LENGTH_LONG).show()
            }
        })
        val btnHospital = findViewById<View>(R.id.btnHospital) as Button
        btnHospital.setOnClickListener(object : View.OnClickListener {
            var Hospital = "hospital"
            override fun onClick(v: View) {
                Log.d("onClick", "Button is Clicked")
                mMap!!.clear()
                val url = getUrl(latitude, longitude, Hospital)
                val DataTransfer = arrayOfNulls<Any>(2)
                DataTransfer[0] = mMap
                DataTransfer[1] = url
                Log.d("onClick", url)
                val getNearbyPlacesData = GetNearbyPlacesData()
                getNearbyPlacesData.execute(*DataTransfer)
                Toast.makeText(this@ACTIVITY_MAPS, "Nearby Hospitals", Toast.LENGTH_LONG).show()
            }
        })
        val btnSchool = findViewById<View>(R.id.btnSchool) as Button
        btnSchool.setOnClickListener(object : View.OnClickListener {
            var School = "school"
            override fun onClick(v: View) {
                Log.d("onClick", "Button is Clicked")
                mMap!!.clear()
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker!!.remove()
                }
                val url = getUrl(latitude, longitude, School)
                val DataTransfer = arrayOfNulls<Any>(2)
                DataTransfer[0] = mMap
                DataTransfer[1] = url
                Log.d("onClick", url)
                val getNearbyPlacesData = GetNearbyPlacesData()
                getNearbyPlacesData.execute(*DataTransfer)
                Toast.makeText(this@ACTIVITY_MAPS, "Nearby Schools", Toast.LENGTH_LONG).show()
            }
        })
    }

    @Synchronized
    protected fun buildGoogleApiClient() {
        mGoogleApiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        mGoogleApiClient!!.connect()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 1000
        mLocationRequest!!.fastestInterval = 1000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) LocationServices.FusedLocationApi.requestLocationUpdates(
            mGoogleApiClient!!,
            mLocationRequest!!, (this as com.google.android.gms.location.LocationListener)
        )
    }

    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {
        val googlePlacesUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlacesUrl.append("location=$latitude,$longitude")
        googlePlacesUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlacesUrl.append("&type=$nearbyPlace")
        googlePlacesUrl.append("&sensor=true")
        googlePlacesUrl.append("&key=" + "AIzaSyATuUiZUkEc_UgHuqsBJa1oqaODI-3mLs0")
        Log.d("getUrl", googlePlacesUrl.toString())
        return googlePlacesUrl.toString()
    }

    override fun onConnectionSuspended(i: Int) {}
    override fun onLocationChanged(location: Location) {
        Log.d("onLocationChanged", "entered")
        mLastLocation = location
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker!!.remove()
        }

        //Place current location marker
        latitude = location.latitude
        longitude = location.longitude
        val latLng = LatLng(location.latitude, location.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
        mCurrLocationMarker = mMap!!.addMarker(markerOptions)

        //move map camera
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
        Toast.makeText(this@ACTIVITY_MAPS, "Your Current Location", Toast.LENGTH_LONG).show()
        Log.d(
            "onLocationChanged",
            String.format("latitude:%.3f longitude:%.3f", latitude, longitude)
        )

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient!!,
                (this as com.google.android.gms.location.LocationListener)
            )
            Log.d("onLocationChanged", "Removing Location Updates")
        }
        Log.d("onLocationChanged", "Exit")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}
    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            true
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient()
                        }
                        mMap!!.isMyLocationEnabled = true
                    }
                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}