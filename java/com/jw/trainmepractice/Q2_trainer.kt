package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

// 추후 트레이너 대한 질문 화면은 아무것도 해주지 않았답니다

class Q2_trainer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q2_trainer)

        val button = findViewById<Button>(R.id.q5_t_nextButton)
        button.setOnClickListener {
            startActivity(Intent(this,Q3_trainer::class.java))
        }
    }
}