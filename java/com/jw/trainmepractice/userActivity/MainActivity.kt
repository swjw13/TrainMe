package com.jw.trainmepractice.userActivity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.jw.trainmepractice.BackPressedForFinish
import com.jw.trainmepractice.MainFragmentFactoryImpl
import com.jw.trainmepractice.R
import com.jw.trainmepractice.TrainerActivity.OnTabItemSelectedListener
import com.jw.trainmepractice.userMainFragment.fragment_1
import com.jw.trainmepractice.userMainFragment.fragment_2
import com.jw.trainmepractice.userMainFragment.fragment_3
import com.jw.trainmepractice.userMainFragment.fragment_4

class MainActivity : AppCompatActivity(), OnTabItemSelectedListener {
    lateinit var backPressedForFinish: BackPressedForFinish
    lateinit var db_realtime: DatabaseReference
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var frag1: fragment_1
    lateinit var frag2: fragment_2
    lateinit var frag3: fragment_3
    lateinit var frag4: fragment_4

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = MainFragmentFactoryImpl(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 이 부분을 유저가 로그인하고 정보 입력을 완료하는 부분에 넣어야 함
//        FirebaseMessaging.getInstance().token.addOnCompleteListener {
//            db_realtime = Firebase.database.reference
//            val token = it.result.toString()
//            val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
//            sp.edit().putString("FCMToken", token).apply()
//            val user_token =
//                getSharedPreferences("user", Context.MODE_PRIVATE).getString("login_token", "null")
//            val realtimeDB = db_realtime.child("user").child(user_token!!)
//            realtimeDB.child("FCMToken").setValue(token)
//        }

        frag1 = fragment_1(this)
        frag2 = fragment_2(this)
        frag3 = fragment_3(this)
        frag4 = fragment_4(this)

        val f1 = supportFragmentManager.fragmentFactory.instantiate(classLoader, fragment_1::class.java.name)
        supportFragmentManager.beginTransaction().replace(R.id.usercontainer, f1).commit()

        bottomNavigation = findViewById(R.id.user_bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.usertab1 -> {
                    val f1 = supportFragmentManager.fragmentFactory.instantiate(classLoader, fragment_1::class.java.name)
                    Toast.makeText(this, "첫 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.usercontainer, f1)
                        .commit()
                    true
                }
                R.id.usertab2 -> {
                    val f2 = supportFragmentManager.fragmentFactory.instantiate(classLoader, fragment_2::class.java.name)
                    Toast.makeText(this, "두 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.usercontainer, f2)
                        .commit()
                    true
                }
                R.id.usertab3 -> {
                    val f3 = supportFragmentManager.fragmentFactory.instantiate(classLoader, fragment_3::class.java.name)
                    Toast.makeText(this, "세 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.usercontainer, f3)
                        .commit()
                    true
                }
                R.id.usertab4 -> {
                    val f4 = supportFragmentManager.fragmentFactory.instantiate(classLoader, fragment_4::class.java.name)
                    Toast.makeText(this, "네 번째 선택됨", Toast.LENGTH_LONG).show()
                    supportFragmentManager.beginTransaction().replace(R.id.usercontainer, f4)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onTabSelected(position: Int) {
        if (position == 0)
            bottomNavigation.selectedItemId = R.id.usertab1
        else if (position == 1)
            bottomNavigation.selectedItemId = R.id.usertab2
        else if (position == 2)
            bottomNavigation.selectedItemId = R.id.usertab3
        else if (position == 4)
            bottomNavigation.selectedItemId = R.id.usertab4
    }

    override fun onBackPressed() {
        // BackPressedForFinish 클래시의 onBackPressed() 함수를 호출한다.
        backPressedForFinish.onBackPressed()
    }

}
