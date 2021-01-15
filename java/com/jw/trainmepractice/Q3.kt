package com.jw.trainmepractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Q3 : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q3)

        db_realtime = Firebase.database.reference

        findViewById<Button>(R.id.q3_nextbutton).setOnClickListener {
            val userBodyInfo = findViewById<EditText>(R.id.ans_userbodyInfo).text.toString()
            val userEatHabit = findViewById<EditText>(R.id.ans_eathabit).text.toString()
            val userSportAvailable = findViewById<EditText>(R.id.ans_sportAvailabe).text.toString()

            if(userBodyInfo.length != 0 && userEatHabit.length != 0 && userSportAvailable.length != 0){

                val real = db_realtime.child("user").child(Firebase.auth.currentUser!!.uid)
                real.child("userBodyInfo").setValue(userBodyInfo)
                real.child("userEatHabit").setValue(userEatHabit)
                real.child("userSportAvailable").setValue(userSportAvailable)

                val sp = getSharedPreferences("myInfo", Activity.MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("userBodyInfo", userBodyInfo)
                editor.putString("userEatHabit", userEatHabit)
                editor.putString("userSportAvailable", userSportAvailable)
                editor.apply()

                startActivity(Intent(this, Q4::class.java))
                // 화면을 바꿀 때 이전 기록을 없애는 기술 필요
            } else{
                makeToast(this,"정보를 입력해 주세요!")
            }
        }
    }
}