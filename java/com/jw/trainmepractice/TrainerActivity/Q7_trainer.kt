package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R
import com.jw.trainmepractice.getAdapter

class Q7_trainer : AppCompatActivity() {

    lateinit var availableTimeList: TextView
    lateinit var scheduleAdditional: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q7_trainer)

        val bundle = intent.extras!!
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지

        val seekbar7 = findViewById<VerticalSeekBar>(R.id.seekbar7)
        seekbar7.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        availableTimeList = findViewById(R.id.avaiableTimeList)
        scheduleAdditional = findViewById(R.id.scheduleAdditional)

        val spinner_day = findViewById<Spinner>(R.id.spinner_day)
        val array_day = listOf("월","화","수","목","금","토","일","평일","주말")
        spinner_day.adapter = getAdapter(this, array_day)

        val spinner_time = findViewById<Spinner>(R.id.spinner_time)
        val array_time = listOf("9시이전","9시","10시","11시","12시","13시","14시","15시","16시","17시","18시","19시","20시","21시","21시이후","하루종일")
        spinner_time.adapter = getAdapter(this, array_time)

        findViewById<FloatingActionButton>(R.id.addButton).setOnClickListener {
            val day = spinner_day.selectedItem.toString()
            val time = spinner_time.selectedItem.toString()

            availableTimeList.append(day + " " + time + "\n")

            spinner_day.setSelection(0)
            spinner_time.setSelection(0)
        }
        findViewById<FloatingActionButton>(R.id.nextButton7).setOnClickListener {
            val time_list = availableTimeList.text.toString()
            val schedule_Additional = scheduleAdditional.text.toString()

            if(time_list.length > 0){
                val intent = Intent(this, Q8_trainer::class.java)
                bundle.putString("available_time",time_list)
                bundle.putString("schedule_additional", schedule_Additional)
                intent.putExtras(bundle)

                startActivity(intent)
            } else{
                Toast.makeText(this, "가능한 시간을 입력해 주세요", Toast.LENGTH_LONG).show()
            }
        }
        findViewById<Button>(R.id.skipButton7).setOnClickListener {
            val time_list = "-"
            val schedule_Additional = "-"

            val intent = Intent(this, Q8_trainer::class.java)
            bundle.putString("available_time",time_list)
            bundle.putString("schedule_additional", schedule_Additional)
            intent.putExtras(bundle)

            startActivity(intent)
        }
    }
}
