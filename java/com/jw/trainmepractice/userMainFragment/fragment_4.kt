package com.jw.trainmepractice.userMainFragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.jw.trainmepractice.Ask_Trainer_or_User
import com.jw.trainmepractice.R
import com.jw.trainmepractice.googleMap


class fragment_4(val fragment: FragmentActivity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_4, container, false)

        viewClick(view)

        return view
    }

    fun viewClick(view: View){
        view.findViewById<Button>(R.id.testbutton).setOnClickListener {
            startActivity(Intent(fragment, Map::class.java))
        }
        view.findViewById<Button>(R.id.togoogleMap).setOnClickListener{
            startActivity(Intent(fragment, googleMap::class.java))
        }
        view.findViewById<Button>(R.id.logoutBtn).setOnClickListener {
            val intent = Intent(fragment, Ask_Trainer_or_User::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            // 로그아웃을 하게 된다면 모든 정보를 삭제한다
            val sp = fragment.getSharedPreferences("user", MODE_PRIVATE)
            sp.edit().clear().apply()

            startActivity(intent)
            fragment.finish()
        }
    }
}
