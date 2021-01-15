package com.jw.trainmepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 로그인 화면
// 아마 코드 그대로 가져온듯...?

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)


        auth = Firebase.auth

        findViewById<Button>(R.id.makeSubmitButton).setOnClickListener {
            val make_email = findViewById<EditText>(R.id.makeEmailEdittext).text.toString()
            val make_password1 = findViewById<EditText>(R.id.makePassword1EditText).text.toString()
            val make_password2 = findViewById<EditText>(R.id.makePassword2EditText).text.toString()

            if(make_email.length > 0 && make_password1.length > 0) {
                if(make_password1 == make_password2){
                    makeEmail(make_email, make_password1)
                } else {
                    makeToast(this, "비밀번호가 일치하지 않습니다")
                }
            }
            else{
                makeToast(this, "이메일을 입력하세요")
            }
        }
    }
    fun makeEmail(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    makeToast(this, "이메일 등록에 성공하였습니다!")
                    startActivity(Intent(this, Ask_Trainer_or_User::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    makeToast(this, "이메일 등록에 실패하였습니다!")
                }
            }
    }

}
