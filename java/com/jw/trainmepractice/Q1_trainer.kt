package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// 트레이너 질문 화면
// 추후 해야 할 것
//      데이터 sharedPreference에 넣기

class Q1_trainer : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_trainer)

        db_realtime = Firebase.database.reference

        val sex_spinner_trainer = findViewById<Spinner>(R.id.sex_spinner_trainer)
        val age_spinner_trainer = findViewById<Spinner>(R.id.age_spinner_trainer)
        val where_spinner_trainer = findViewById<Spinner>(R.id.where_spinner_trainer)

        val array_sex_trainer = listOf("남자", "여자")
        val array_age_trainer = listOf("10대 미만","10대","20대","30대","40대","50대","60대 이상")
        val array_where_trainer = listOf("서울","대구","부산","울산")

        sex_spinner_trainer.adapter = getAdapter(this,array_sex_trainer)
        age_spinner_trainer.adapter = getAdapter(this,array_age_trainer)
        where_spinner_trainer.adapter = getAdapter(this, array_where_trainer)

        findViewById<Button>(R.id.q1_nextbutton_trainer).setOnClickListener {
            val sex = sex_spinner_trainer.selectedItem.toString()
            val age = age_spinner_trainer.selectedItem.toString()
            val where = where_spinner_trainer.selectedItem.toString()

            db_realtime.child("trainer")
                .child(Firebase.auth.currentUser!!.uid)
                .setValue(mapOf(
                    "sex" to sex,
                    "age" to age,
                    "where" to where,
                    "userID" to Firebase.auth.currentUser!!.uid
                ))

            startActivity(Intent(this, Q2_trainer::class.java))
        }
    }

}