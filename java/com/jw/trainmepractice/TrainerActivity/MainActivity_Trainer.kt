package com.jw.trainmepractice.TrainerActivity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.jw.trainmepractice.BackPressedForFinish
import com.jw.trainmepractice.MainFragmentFactoryImpl
import com.jw.trainmepractice.R
import com.jw.trainmepractice.trainerMainFragment.fragment1_trainer
import com.jw.trainmepractice.trainerMainFragment.fragment2_trainer
import com.jw.trainmepractice.trainerMainFragment.fragment3_trainer

class MainActivity_Trainer : AppCompatActivity(), OnTabItemSelectedListener {

    lateinit var backPressedForFinish: BackPressedForFinish
    lateinit var fragment1: fragment1_trainer
    lateinit var fragment2: fragment2_trainer
    lateinit var fragment3: fragment3_trainer
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var db_realtime: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = MainFragmentFactoryImpl(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main__trainer)

        // FCM을 위한 토큰을 입력받아서 저장
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            db_realtime = Firebase.database.reference
            val token = it.result.toString()
            val sp = getSharedPreferences("trainer", Context.MODE_PRIVATE)
            sp.edit().putString("FCMToken",token).apply()
            val user_token =
                getSharedPreferences("trainer", Context.MODE_PRIVATE).getString("login_token", "null")
            val realtimeDB = db_realtime.child("trainer").child(user_token!!)
            realtimeDB.child("FCMToken").setValue(token)
        }

        fragment1 = fragment1_trainer(this)
        fragment2 = fragment2_trainer(this)
        fragment3 = fragment3_trainer(this)

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment1).commit()

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            when(item.itemId) {
                R.id.tab1 -> {
                    Toast.makeText(this,"첫 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.container,fragment1).commit()
                    true
                }
                R.id.tab2 -> {
                    Toast.makeText(this,"두 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.container,fragment2).commit()
                    true
                }
                R.id.tab3 -> {
                    Toast.makeText(this,"세 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.container,fragment3).commit()
                    true
                }
                else -> false
            }
        }
    }
    override fun onTabSelected(position: Int) {
        if(position == 0)
            bottomNavigation.selectedItemId = R.id.tab1
        else if(position == 1)
            bottomNavigation.selectedItemId = R.id.tab2
        else if(position == 2)
            bottomNavigation.selectedItemId = R.id.tab3
    }

    override fun onBackPressed() {
        // BackPressedForFinish 클래시의 onBackPressed() 함수를 호출한다.
        backPressedForFinish.onBackPressed()
    }
}