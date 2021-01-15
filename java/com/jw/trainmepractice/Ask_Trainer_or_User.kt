package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

// 맨 처음 화면
// 트레이너인지 일반 유저인지 물어본다
// 만약 트레이너라면 트레이너 전용 로그인 화면으로 이동하고
// 일반 유저라면 유저 전용 로그인 화면(비회원 전용) 으로 이동한다
// 지금은 이거를 첫 화면으로 쓸지, 아니면 로그인 화면이랑 합칠지(합치게 되면 아마 버튼을 클릭하면 로그인창이 샤랄라 하고 나타나는 animation을 사용할 듯 하다)

class Ask_Trainer_or_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ask_trainer_or_user)

        findViewById<CardView>(R.id.user_button).setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
        findViewById<CardView>(R.id.trainer_button).setOnClickListener {
            startActivity(Intent(this, Login_trainer::class.java))
        }
    }
}