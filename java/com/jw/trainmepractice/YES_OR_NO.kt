package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

// 개인정보를 입력할지 말지 정하는 화면
// 처음에는 입력을 한다 그러면 Q1.kt 이나 Q1_trainer.kt로 가고
// 안한다 그러면 DB에는 여백을 저장해서 MainActivity로 갔는데
// 비회원 부분을 생각하면 로직의 변화가 필요할듯..?

class YES_OR_NO : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wesorno)

        findViewById<Button>(R.id.yes_button).setOnClickListener {
            startActivity(Intent(this, Q1::class.java))
            finish()
        }
        findViewById<Button>(R.id.no_button).setOnClickListener {
            val realtimeDB = db_realtime
                .child("user")
                .child(Firebase.auth.currentUser!!.uid)
                .setValue(mapOf(
                    "name" to "  ",
                    "age" to " ",
                    "sex" to "  ",
                    "where" to "  ",
                    "height" to "  ",
                    "weight" to "  ",
                    "weightprofile" to "  ",
                    "userBodyInfo" to "  ",
                    "userEatHabit" to "  ",
                    "userSportAvailable" to "  " ,
                    "time" to "  ",
                    "goal" to "  ",
                    "extra" to "  ",
                    "matchedTrainer" to arrayListOf<Trainer>()
                ))

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}