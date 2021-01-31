package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Q4_trainer : AppCompatActivity() {
    lateinit var map: GoogleMap
    lateinit var myMarker: MarkerOptions
    lateinit var db_realtime: DatabaseReference

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q4_trainer)

        val bundle = intent.extras!!
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지

        val editText = findViewById<EditText>(R.id.addressEditText)
        val rg = findViewById<RadioGroup>(R.id.yesNoRadio)

        val seekbar4 = findViewById<VerticalSeekBar>(R.id.seekbar4)
        seekbar4.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFrag) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            @SuppressLint("MissingPermission")
            override fun onMapReady(p0: GoogleMap?) {
                map = p0!!
                map.setMyLocationEnabled(true)
            }
        })
        MapsInitializer.initialize(this)

        findViewById<Button>(R.id.findAddressButton).setOnClickListener {
            val loc: Location? = getLocationFromAddress(this, editText.text.toString())
            if (loc != null)
                showCurrentLocation(loc)
            else {
                Toast.makeText(this, "검색 결과 없음", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<FloatingActionButton>(R.id.nextButton4).setOnClickListener {
            val get_from_radiogroupd = rg.checkedRadioButtonId
            val available = when(get_from_radiogroupd){
                R.id.yes -> "Yes"
                R.id.no -> "No"
                else -> "No"
            }
            val position = getLocationFromAddress(this, editText.text.toString())?.latitude.toString() + " / " + getLocationFromAddress(this, editText.text.toString())?.longitude.toString()

            if(position != null){
                val intent = Intent(this, Q5_trainer::class.java)
                bundle.putString("if_available_outside",available)
                bundle.putString("centerPosition",position)
                intent.putExtras(bundle)
                startActivity(intent)
            } else{
                Toast.makeText(this, "위치를 입력 해 주세요",Toast.LENGTH_LONG).show()
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

    private fun showCurrentLocation(location: Location) {
        val curPoint = LatLng(location.latitude, location.longitude)
        val msg = "Latitutde : " + curPoint.latitude + "\nLongitude : " + curPoint.longitude
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        //화면 확대, 숫자가 클수록 확대
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15.toFloat()))

        //마커 찍기
        showMyMarker(curPoint)
    }

    private fun showMyMarker(location: LatLng) {

        myMarker = MarkerOptions()
        myMarker.position(location)
        myMarker.title("◎ 내위치\n")
        myMarker.snippet("내 위치?")
        map.addMarker(myMarker)
    }
}