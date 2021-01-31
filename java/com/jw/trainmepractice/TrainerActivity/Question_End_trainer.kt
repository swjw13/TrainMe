package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Question_End_trainer : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question__end_trainer)

        val sp = getSharedPreferences("trainer", Context.MODE_PRIVATE)

        db_realtime = Firebase.database.reference

        val seekbarEnd = findViewById<VerticalSeekBar>(R.id.seekbarEnd)
        seekbarEnd.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        val bundle = intent.extras!!

        val login_token = bundle.getString("login_token")
        val name = bundle.getString("name")
        val age = bundle.getString("age")
        val sex = bundle.getString("sex")
        //val tel = bundle.getString("tel")
        //val address = bundle.getString("address")
        val oneLineInfo = bundle.getString("oneLineInfo")
        val specificInfo = bundle.getString("specific_Info")
        val if_available_outside = bundle.getString("if_available_outside")
        val mainPosition = bundle.getString("centerPosition")
        val programName = bundle.getString("programName")
        val trainerMajor = bundle.getString("trainerMajor")
        val programAdditional = bundle.getString("program_additional")
        val availabeTime = bundle.getString("available_time")
        val scheduleAdditional = bundle.getString("schedule_additional")
        val ptType = bundle.getString("ptType")
        val price10 = bundle.getString("price_10times")
        val price20 = bundle.getString("price_20times")
        val timePerOne = bundle.getString("time_per_one")
        val additionalPrice = bundle.getString("additional_price")
        val priceSpecific = bundle.getString("price_specific")
        val sns = bundle.getString("sns")

        putInDB("login_token",login_token)
        putInDB("name",name)
        putInDB("age",age)
        putInDB("sex",sex)
        //putInDB("tel",tel)
        //putInDB("address",address)
        putInDB("oneLineInfo",oneLineInfo)
        putInDB("specificInfo",specificInfo)
        putInDB("if_available_outside",if_available_outside)
        putInDB("mainPosition",mainPosition)
        putInDB("programName",programName)
        putInDB("trainerMajor",trainerMajor)
        putInDB("programAdditional",programAdditional)
        putInDB("availabeTime",availabeTime)
        putInDB("scheduleAdditional",scheduleAdditional)
        putInDB("ptType",ptType)
        putInDB("price10",price10)
        putInDB("price20",price20)
        putInDB("timePerOne",timePerOne)
        putInDB("additionalPrice",additionalPrice)
        putInDB("priceSpecific",priceSpecific)
        putInDB("sns",sns)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            db_realtime = Firebase.database.reference
            val token = it.result.toString()
            val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
            sp.edit().putString("FCMToken", token).apply()
            val user_token =
                    getSharedPreferences("trainer", Context.MODE_PRIVATE).getString("login_token", "null")
            val realtimeDB = db_realtime.child("trainer").child(user_token!!)
            realtimeDB.child("FCMToken").setValue(token)
        }

        // 추후 등록됐음을 알려주기 위한 코드
        val editor = sp.edit()
        editor.putString("uploadClear","1").apply()

        findViewById<CardView>(R.id.card).setOnClickListener {
            val intent = Intent(this, MainActivity_Trainer::class.java)
            startActivity(intent)
        }
    }

    fun putInDB(key: String, value: String?){
        val user_token = getSharedPreferences("trainer", Context.MODE_PRIVATE).getString("login_token", "null")
        val realtimeDB = db_realtime.child("trainer").child(user_token!!).child("profile")

        realtimeDB.child(key).setValue(value)
    }
}