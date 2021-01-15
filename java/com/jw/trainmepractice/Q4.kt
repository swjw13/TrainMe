package com.jw.trainmepractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Q4 : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q4)

        db_realtime = Firebase.database.reference
        val weekday_spinner = findViewById<Spinner>(R.id.ans_weekday)
        val time_spinner = findViewById<Spinner>(R.id.ans_time)
        val array_weekday = listOf("월요일","화요일","수요일","목요일","금요일","토요일","일요일","상관없음")
        val array_time = listOf("9-10","10-11","11-12","12-13","13-14","14-15","15-16","16-17","17-18","18-19","19-20","20-21","상관없음")
        weekday_spinner.adapter = getAdapter(this, array_weekday)
        time_spinner.adapter = getAdapter(this, array_time)
        val textview = findViewById<TextView>(R.id.availabledayList)

        findViewById<FloatingActionButton>(R.id.addTimeButton).setOnClickListener {
            val tmp = weekday_spinner.selectedItem.toString() + " / " + time_spinner.selectedItem.toString()
            textview.append(tmp + " \r\n ")
            weekday_spinner.setSelection(0)
            time_spinner.setSelection(0)
        }

        findViewById<Button>(R.id.q4_nextbutton).setOnClickListener {

            val goal = findViewById<EditText>(R.id.ans_goal).text.toString()
            val extra = findViewById<EditText>(R.id.ans_extra).text.toString()

            val time = textview.text.toString()


            val real = db_realtime.child("user").child(Firebase.auth.currentUser!!.uid)
            real.child("goal").setValue(goal)
            real.child("extra").setValue(extra)
            real.child("time").setValue(time)
            real.child("matchedTrainer").setValue(arrayListOf<String>())

            val sp = getSharedPreferences("myInfo", Activity.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("goal", goal)
            editor.putString("extra", extra)
            editor.putString("time", time)
            editor.apply()

            startActivity(Intent(this, MainActivity::class.java))
            // 화면을 바꿀 때 이전 기록을 없애는 기술 필요
        }
    }
}