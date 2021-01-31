package com.jw.trainmepractice.userActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.jw.trainmepractice.R
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // 로그인 공통 callback 구성
        val callback: ((OAuthToken?, Throwable?) -> Unit) = { token, error ->
            if (error != null) {
                //Login Fail
            } else if (token != null) {
                //Login Success

                // 로그인을 하게 된다면 해당 계정의 token값과 FCMToken값을 개인 DB에 저장한다
                // 추후에 이 값을 가지고 main / Q1 둘중 하나로 가도록 만들어야함

                val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
                sp.edit().putString("login_token", token.accessToken).apply()
                FirebaseMessaging.getInstance().token.addOnCompleteListener {
                    val db_realtime = Firebase.database.reference
                    val token = it.result.toString()
                    val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
                    sp.edit().putString("FCMToken", token).apply()
                    val user_token = getSharedPreferences("user", Context.MODE_PRIVATE).getString("login_token", "null")
                    val realtimeDB = db_realtime.child("user").child(user_token!!)
                    realtimeDB.child("FCMToken").setValue(token)
                }

                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        findViewById<ImageView>(R.id.loginButton).setOnClickListener {
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@Login)) {
                    loginWithKakaoTalk(this@Login, callback = callback)
                } else {
                    loginWithKakaoAccount(this@Login, callback = callback)
                }
            }
        }
    }
}