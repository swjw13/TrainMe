package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

// 앱 시작 Splash화면
// 사진을 바꿔주거나 시간을 조절하는 기능 정도 할수있음

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        Handler().postDelayed({
            startActivity(Intent(this, Login::class.java))
            finish()
        }, 3000)
         }
}