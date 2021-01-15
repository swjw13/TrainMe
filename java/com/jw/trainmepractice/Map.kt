package com.jw.trainmepractice

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import net.daum.mf.map.api.MapView

// 카카오맵
// 기능 추가할까?

class Map : AppCompatActivity() {

    lateinit var mapViewContainer: ViewGroup
    lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mappractice)

        mapView = MapView(this)
        mapViewContainer = findViewById<View>(R.id.map_view) as ViewGroup
        mapViewContainer.addView(mapView)
    }
}