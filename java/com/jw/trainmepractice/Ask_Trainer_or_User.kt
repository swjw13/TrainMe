package com.jw.trainmepractice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jw.trainmepractice.TrainerActivity.Login_trainer
import com.jw.trainmepractice.userActivity.MainActivity
import com.pedro.library.AutoPermissions

class Ask_Trainer_or_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ask_trainer_or_user)
        AutoPermissions.loadAllPermissions(this, 101)

        findViewById<Button>(R.id.user_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.trainer_button).setOnClickListener {
            startActivity(Intent(this, Login_trainer::class.java))
            finish()
        }
    }
}