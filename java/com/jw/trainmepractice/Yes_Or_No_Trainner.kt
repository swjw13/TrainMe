package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

// 트레이너에게 개인정보 올릴건지 말지 결정하는 화면
// 지금 생각해보니 괜히 만든거같다

class Yes_Or_No_Trainner : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yes_or_no_trainer)

        findViewById<Button>(R.id.yes_button_trainer).setOnClickListener {
            startActivity(Intent(this, Q1_trainer::class.java))
            finish()
        }
        findViewById<Button>(R.id.no_button_trainer).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}