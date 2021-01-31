package com.jw.trainmepractice.userMainFragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.memberClass.Trainer
import com.jw.trainmepractice.memberClass.trainerAll

class fragment_2(val fragment: FragmentActivity) : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var mapF: SupportMapFragment
    private lateinit var map: GoogleMap

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mapView.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2_showall, container, false)

        mapView = view.findViewById(R.id.map) as MapView
        mapView.getMapAsync(this)
        //MapsInitializer.initialize(fragment)

        val adapter = fragment2_adapter(
            LayoutInflater.from(fragment),
            fragment
        )

        val listView = view.findViewById<RecyclerView>(R.id.frag2_list)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(fragment)


        // 전체 트레이너의 목록을 보여줌
        // 추후에 조건으로 분류를 시켜 줄 예정
        val db_realtime = Firebase.database.reference
        db_realtime.child("trainer").addChildEventListener(object: ChildEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(trainerAll::class.java)?.profile?.let { adapter.update(it) }
                adapter.notifyDataSetChanged()
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                snapshot.getValue(trainerAll::class.java)?.profile?.let { adapter.delete(it) }
                adapter.notifyDataSetChanged()
            }
        })

        return view
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        map = p0!!
        requestMyLocation()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
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

        val manager = fragment.getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
        Toast.makeText(fragment, msg, Toast.LENGTH_SHORT).show()

        //화면 확대, 숫자가 클수록 확대
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15.toFloat()))
        showMyMarker(curPoint)
    }
    private fun showMyMarker(location: LatLng) {

        val myMarker = MarkerOptions()
        myMarker.position(location)
        map.addMarker(myMarker)
    }
}
class fragment2_adapter(
    val inflater: LayoutInflater,
    val fragment: FragmentActivity
) : RecyclerView.Adapter<fragment2_adapter.ViewHolder>() {

    var list = arrayListOf<Trainer>()

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val image = itemview.findViewById<ImageView>(R.id.frag2_image)
        val name = itemview.findViewById<TextView>(R.id.frag2_name)
        val master = itemview.findViewById<TextView>(R.id.frag2_master)
        val info = itemview.findViewById<TextView>(R.id.frag2_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.frag2_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(list[position].name)
        holder.master.setText(list[position].trainerMajor)
        holder.info.setText(list[position].oneLineInfo)
        holder.image.setOnClickListener {
            val intent = Intent(fragment, frag2_trainerProfile::class.java)
            intent.putExtra("trainerId", list[position].id)
            fragment.startActivity(intent)
        }
    }
    fun update(item: Trainer){
        list.add(item)
    }
    fun delete(item: Trainer){
        list.remove(item)
    }
}