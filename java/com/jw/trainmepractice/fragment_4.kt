package com.jw.trainmepractice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

// 4번재 탭을 위한 fragment
// 현재는 지도만을 보여주고있음

class fragment_4(val fragment: FragmentActivity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_4, container, false)

        view.findViewById<Button>(R.id.testbutton).setOnClickListener {
            startActivity(Intent(fragment, Map::class.java))
        }
        return view
    }
}
