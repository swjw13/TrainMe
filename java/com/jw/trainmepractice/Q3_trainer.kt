package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Q3_trainer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q3_trainer)

        val button = findViewById<Button>(R.id.q5_t_nextButton)
        button.setOnClickListener {
            startActivity(Intent(this,Q4_trainer::class.java))
        }
    }
}