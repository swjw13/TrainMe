package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Q1_trainer : AppCompatActivity() {

    lateinit var seekBar1: VerticalSeekBar
    lateinit var db_realtime: DatabaseReference

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1_trainer)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지

        db_realtime = Firebase.database.reference
        seekBar1 = findViewById(R.id.seekBar1)

        findViewById<FloatingActionButton>(R.id.trainer_q1_nextButton).setOnClickListener {
            val trainer_age = findViewById<EditText>(R.id.trainer_age_input).text.toString()
            val trainer_sex = findViewById<EditText>(R.id.trainer_sex_input).text.toString()
            val trainer_name = findViewById<EditText>(R.id.trainer_name).text.toString()

            try{
                if(trainer_age.toInt() in 0..100){
                    if(trainer_sex in arrayOf("남","여")){
                        if(trainer_name.length > 0){
                            val user_token = getSharedPreferences("trainer", Context.MODE_PRIVATE).getString("login_token","null")

                            val intent = Intent(this, Q2_trainer::class.java)
                            val bundle = Bundle()
                            bundle.putString("age",trainer_age)
                            bundle.putString("sex",trainer_sex)
                            bundle.putString("name",trainer_name)
                            bundle.putString("login_token",user_token)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        } else{
                            Toast.makeText(this, "이름을 입력해 주세요",Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "성별을 잘못 입력하셨습니다",Toast.LENGTH_LONG).show()
                    }
                } else{
                    Toast.makeText(this,"나이를 잘못 입력하셨습니다",Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception){
                Toast.makeText(this,"나이를 제대로 입력해 주세요", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}