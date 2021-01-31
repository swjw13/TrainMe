package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R

class Q10_trainer : AppCompatActivity() {

    lateinit var snsEditText : EditText
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q10_trainer)

        val bundle = intent.extras!!
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지

        val seekbar10 = findViewById<VerticalSeekBar>(R.id.seekbar10)
        seekbar10.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        snsEditText = findViewById(R.id.snsEditText)

        findViewById<FloatingActionButton>(R.id.nextButton10).setOnClickListener {
            val sns = snsEditText.text.toString()

            val intent = Intent(this, Question_End_trainer::class.java)
            bundle.putString("sns",sns)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        findViewById<Button>(R.id.skipButton10).setOnClickListener {
            val sns = "-"
            val intent = Intent(this, Question_End_trainer::class.java)
            bundle.putString("sns",sns)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}