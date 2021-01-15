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

private lateinit var auth: FirebaseAuth

// 유저의 로그인 화면
// 비회원 로그인 부분을 야매로 만들어놓음
// 원래는 로그인 버튼(submit_button)을 누르면 email, password를 가지고 MainActivity로 가고
// 회원가입 버튼(gotoRegisterButton)을 누르면 회원가입 화면으로 가게 만들었는데
// 추후 로직 변경이 필요할거 같음(누르면 어디로 가는지, 회원정보를 등록 하는지 등등? )


class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth

        findViewById<Button>(R.id.gotoRegisterButton).setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.submitButton).setOnClickListener {
            signin()
        }
        findViewById<TextView>(R.id.use_without_login).setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    fun signin() {
        val email = findViewById<EditText>(R.id.emailEditText).text.toString()
        val password = findViewById<EditText>(R.id.passwordEditText).text.toString()

        if (email.length > 0 && password.length > 0) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //val user = auth.currentUser!!.getIdToken(true)
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
        val intent = Intent(this@Login, MainActivity::class.java)
        startActivity(intent)
    }

}