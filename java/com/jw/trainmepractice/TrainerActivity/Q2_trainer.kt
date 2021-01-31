package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Q2_trainer : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q2_trainer)

        val bundle = intent.extras!!

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지
        val seekbar2 = findViewById<VerticalSeekBar>(R.id.seekbar2)

        // 시크바 애니메이션 막기(그냥 보여주기 용도로 사용)
        seekbar2.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })
        val countText = findViewById<TextView>(R.id.textcount2)
        val trainer_oneline_info = findViewById<EditText>(R.id.trainer_oneline_info)

        trainer_oneline_info.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                countText.setText(""+trainer_oneline_info.text.toString().length + " / " + " 10")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        findViewById<FloatingActionButton>(R.id.trainer_q2_nextbutton).setOnClickListener {
            val one_list_info = trainer_oneline_info.text.toString()
            if(one_list_info.length >= 5){
                bundle.putString("oneLineInfo",one_list_info)
                val intent = Intent(this, Q3_trainer::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            } else{
                Toast.makeText(this,"최소 10글자는 입력해 주세요!",Toast.LENGTH_LONG).show()
            }
        }
    }
}