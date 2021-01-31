package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Q3_trainer : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q3_trainer)

        val bundle = intent.extras!!

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지
        val seekbar3 = findViewById<VerticalSeekBar>(R.id.seekbar3)

        // 시크바 애니메이션 막기(그냥 보여주기 용도로 사용)
        seekbar3.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        val info_editText = findViewById<EditText>(R.id.trainer_specific_info)
        val countText3 = findViewById<TextView>(R.id.textcount3)
        info_editText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                countText3.setText("" + info_editText.text.toString().length + " / " + "300")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        findViewById<FloatingActionButton>(R.id.nextButton3).setOnClickListener {
            val specificInfo = info_editText.text.toString()

            if(specificInfo.length <= 300){
                if(specificInfo.length >= 10){
                    val intent = Intent(this, Q4_trainer::class.java)
                    bundle.putString("specific_Info",specificInfo)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else{
                    Toast.makeText(this, "적어도 10글자는 입력 해 주세요!",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "글자를 너무 많이 입력하셨습니다",Toast.LENGTH_LONG).show()
            }

        }
        findViewById<Button>(R.id.trainer_q3_skipButton).setOnClickListener {
            val intent = Intent(this, Q4_trainer::class.java)
            bundle.putString("specific_Info","-")
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}