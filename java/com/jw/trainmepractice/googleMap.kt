package com.jw.trainmepractice

import android.annotation.SuppressLint
import android.content.Context
import android.location.*
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pedro.library.AutoPermissions


class googleMap : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var map: GoogleMap
    private lateinit var btnLocation: Button
    private lateinit var btnKor2Loc: Button
    private lateinit var editText: EditText
    private lateinit var myMarker: MarkerOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_map)

        AutoPermissions.loadAllPermissions(this, 101)

        editText = findViewById(R.id.findEditText);
        btnLocation = findViewById(R.id.button1);
        btnKor2Loc = findViewById(R.id.button2);

        mapFragment = supportFragmentManager.findFragmentById(R.id.googlemap) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            @SuppressLint("MissingPermission")
            override fun onMapReady(p0: GoogleMap?) {
                map = p0!!
                map.setMyLocationEnabled(true)
                requestMyLocation()
//                val SEOUL = LatLng(37.56, 126.97)
//                map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL))
//                map.moveCamera(CameraUpdateFactory.zoomTo(13.toFloat()))
            }
        })
        MapsInitializer.initialize(this)

        btnLocation.setOnClickListener {
            requestMyLocation()
        }

        btnKor2Loc.setOnClickListener {
            val loc: Location? = getLocationFromAddress(this, editText.text.toString())
            if (loc != null)
                showCurrentLocation(loc)
            else {
                Toast.makeText(this, "검색 결과 없음", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getLocationFromAddress(
        context: Context,
        address: String
    ): Location? {
        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val resLocation = Location("")
        try {
            addresses = geocoder.getFromLocationName(address, 5)
            if (addresses == null || addresses.size == 0) {
                return null
            }
            val addressLoc: Address = addresses[0]
            resLocation.latitude = addressLoc.getLatitude()
            resLocation.longitude = addressLoc.getLongitude()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resLocation
    }

    @SuppressLint("MissingPermission")
    private fun requestMyLocation() {

        val manager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        var location: Location? = null
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (location != null) {
                showCurrentLocation(location);
            }
        } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                showCurrentLocation(location);
            }

            try {
                val minTime: Long = 10 //갱신 시간
                val minDistance = 0.toFloat() //갱신에 필요한 최소 거리
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            showCurrentLocation(location)
                        }

                        override fun onStatusChanged(
                            s: String,
                            i: Int,
                            bundle: Bundle
                        ) {
                        }

                        override fun onProviderEnabled(s: String) {}
                        override fun onProviderDisabled(s: String) {}
                    })
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    private fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        val msg = "Latitutde : " + curPoint.latitude + "\nLongitude : " + curPoint.longitude
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        //화면 확대, 숫자가 클수록 확대
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15.toFloat()))

        //마커 찍기
//        val targetLocation = Location("")
//        targetLocation.latitude = location.latitude
//        targetLocation.longitude = location.longitude
        showMyMarker(curPoint)
    }

    private fun showMyMarker(location: LatLng) {

        myMarker = MarkerOptions()
        myMarker.position(location)
        myMarker.title("◎ 내위치\n")
        myMarker.snippet("여기가 어디지?")
        //myMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation))
        map.addMarker(myMarker)
    }
}