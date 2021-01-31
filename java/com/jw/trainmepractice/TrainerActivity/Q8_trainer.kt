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

class Q8_trainer : AppCompatActivity() {

    lateinit var ptTypeSpinner: Spinner
    lateinit var price_10timesEditText: EditText
    lateinit var price_20timesEditTExt: EditText
    lateinit var time_per_oneEditText: EditText
    lateinit var additional_Pirce_Radio: RadioGroup
    lateinit var price_specific_EditText: EditText

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q8_trainer)

        val bundle = intent.extras!!
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지

        val seekbar8 = findViewById<VerticalSeekBar>(R.id.seekbar8)
        seekbar8.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        ptTypeSpinner = findViewById(R.id.Pt_type_spinner)
        val ptTypeArray = listOf("1대1 PT", "1대2 PT", "1대다 PT")
        ptTypeSpinner.adapter = getAdapter(this, ptTypeArray)

        price_10timesEditText = findViewById(R.id.price_10timesEditText)
        price_20timesEditTExt = findViewById(R.id.price_20timeEditText)
        time_per_oneEditText = findViewById(R.id.time_per_Once)
        additional_Pirce_Radio = findViewById(R.id.if_additional_money_radio)
        price_specific_EditText = findViewById(R.id.price_specific)

        findViewById<FloatingActionButton>(R.id.nextButton8).setOnClickListener {
            val ptType = ptTypeSpinner.selectedItem.toString()
            val price_10times = price_10timesEditText.text.toString()
            val price_20times = price_20timesEditTExt.text.toString()
            val time_per_one = time_per_oneEditText.text.toString()
            val AdditionalPrice = when(additional_Pirce_Radio.checkedRadioButtonId){
                R.id.additional_money_yes -> "YES"
                R.id.additional_money_no -> "No"
                else -> "YES"
            }
            val price_specific = price_specific_EditText.text.toString()

            if(price_10times.length > 0 && price_20times.length > 0){
                if(time_per_one.length > 0){
                    if(AdditionalPrice.length > 0){
                        if(price_specific.length > 0){
                            // Q9(자격증, 수상 경력)은 내가 몰라서 스킵
                            val intent = Intent(this, Q10_trainer::class.java)
                            bundle.putString("ptType",ptType)
                            bundle.putString("price_10times",price_10times)
                            bundle.putString("price_20times",price_20times)
                            bundle.putString("time_per_one",time_per_one)
                            bundle.putString("additional_price",AdditionalPrice)
                            bundle.putString("price_specific",price_specific)
                            intent.putExtras(bundle)

                            startActivity(intent)
                        } else{
                            Toast.makeText(this, "추가사항을 입력해 주세요",Toast.LENGTH_LONG).show()
                        }
                    } else{
                        Toast.makeText(this, "추가금액 발생 여부를 알려주세요",Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "시간을 입력 해 주세요",Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "가격을 입력해 주세요",Toast.LENGTH_LONG).show()
            }
        }
        findViewById<Button>(R.id.skipButton8).setOnClickListener {
            val ptType = "-"
            val price_10times = "-"
            val price_20times = "-"
            val time_per_one = "-"
            val AdditionalPrice = "-"
            val price_specific = "-"

            val intent = Intent(this, Q10_trainer::class.java)
            bundle.putString("ptType",ptType)
            bundle.putString("price_10times",price_10times)
            bundle.putString("price_20times",price_20times)
            bundle.putString("time_per_one",time_per_one)
            bundle.putString("additional_price",AdditionalPrice)
            bundle.putString("price_specific",price_specific)
            intent.putExtras(bundle)

            startActivity(intent)
        }

    }
}