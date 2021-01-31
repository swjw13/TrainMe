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

class Register_trainer : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_trainer)

        auth = Firebase.auth

        findViewById<Button>(R.id.makeSubmitButton_trainer).setOnClickListener {
            val make_email = findViewById<EditText>(R.id.makeEmailEdittext_trainer).text.toString()
            val make_password1 =
                findViewById<EditText>(R.id.makePassword1EditText_trainer).text.toString()
            val make_password2 =
                findViewById<EditText>(R.id.makePassword2EditText_trainer).text.toString()

            if (make_email.length > 0 && make_password1.length > 0) {
                if (make_password1 == make_password2) {
                    makeEmail(make_email, make_password1)
                } else {
                    makeToast(this, "비밀번호가 일치하지 않습니다")
                }
            } else {
                makeToast(this, "이메일을 입력하세요")
            }
        }
    }

    fun makeEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    makeToast(this, "이메일 등록에 성공하였습니다!")
                    val sp = getSharedPreferences("trainer", Context.MODE_PRIVATE)
                    val editor = sp.edit()

                    editor.putString("login_token", Firebase.auth.currentUser?.uid)
                    editor.apply()

                    startActivity(Intent(this, Start_Makeprofile::class.java))
                    finish()
                } else {
                    makeToast(this, "이메일 등록에 실패하였습니다!")
                }
            }
    }
}