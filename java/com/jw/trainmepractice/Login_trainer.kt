package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 즉석에서 복붙한 트레이너를 위한 화면
// 트레이너의 경우에는 질문에 대한 답을 반드시 받아야 하기 때문에 비회원 화면을 안만들거고
// 추후에 일반회원 화면이랑 화면을 공유할 방법이 있는지 알아볼 예정
// 지금은 근데 아직 트레이너 질문 화면을 다 안만들어서
// 크게 동작하는 부분은 없을 거 같습니당


class Login_trainer : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_trainer)

        auth = Firebase.auth

        findViewById<Button>(R.id.gotoRegisterButton_trianer).setOnClickListener {
            val intent = Intent(this@Login_trainer, Register::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.submitButton_trianer).setOnClickListener {
            signin()
        }

    }

    fun signin() {
        val email = findViewById<EditText>(R.id.emailEditText_trianer).text.toString()
        val password = findViewById<EditText>(R.id.passwordEditText_trianer).text.toString()


        // 현재 email / password를 거르는 방법이 아무것도 입력을 안 했을 때 거르는 것밖에 안만들었음
        // 추후에 email 형태를 확인한다던가, 다른 제한을 가하는 방법이 필요할 거 같음

        if (email.length > 0 && password.length > 0) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            makeToast(this, "로그인에 성공하셨습니다!")
                            startToMainActivity()
                            finish()
                        } else {
                            if(task.getException() != null) {
                                makeToast(this, "이메일 혹은 패스워드가 일치하지 않습니다")
                            }
                        }
                    }
        } else {
            makeToast(this, "이메일을 입력해라")
        }
    }

    fun startToMainActivity(){
        // 메인 화면을 구분을 시켜줄 거면 여기서 트레이너와 고객을 구분시켜 줄 방법이 필요하다
        val intent = Intent(this@Login_trainer, MainActivity::class.java)
        startActivity(intent)
    }
}