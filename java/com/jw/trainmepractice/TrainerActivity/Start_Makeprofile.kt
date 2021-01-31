package com.jw.trainmepractice.TrainerActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jw.trainmepractice.R

class Start_Makeprofile : AppCompatActivity() {
    lateinit var makeProfileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start__makeprofile)

        makeProfileButton = findViewById(R.id.trainer_makeProfileButton)
        makeProfileButton.setOnClickListener {
            startActivity(Intent(this, Q1_trainer::class.java))
        }
    }
}