package com.jw.trainmepractice.TrainerActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.makeToast

// 일단은 기본 로그인 화면
// 추후에 카카오로그인으로 업데이트

class Login_trainer : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_trainer)

        auth = Firebase.auth

        findViewById<Button>(R.id.gotoRegisterButton_trianer).setOnClickListener {
            startActivity(Intent(this, Register_trainer::class.java))
        }
        findViewById<Button>(R.id.submitButton_trianer).setOnClickListener {
            signin()
        }
    }

    fun signin() {
        val email = findViewById<EditText>(R.id.emailEditText_trianer).text.toString()
        val password = findViewById<EditText>(R.id.passwordEditText_trianer).text.toString()

        if (email.length > 0 && password.length > 0) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        makeToast(
                            this,
                            "로그인에 성공하셨습니다!"
                        )
                        // 추후: 자동로그인 구현
                        val sp = getSharedPreferences("trainer", Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.putString("login_token", Firebase.auth.currentUser?.uid)
                        editor.apply()

                        // 추후: 프로필을 등록 한 사람은 바로 메인으로 가도록 변경
                        startActivity(Intent(this, Start_Makeprofile::class.java))
                        finish()

                    } else {
                        if (task.getException() != null) {
                            makeToast(
                                this,
                                "이메일 혹은 패스워드가 일치하지 않습니다"
                            )
                        }
                    }
                }
        } else {
            makeToast(this, "이메일을 입력해 주세요")
        }
    }
}