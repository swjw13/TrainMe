package com.jw.trainmepractice.TrainerActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar
import com.jw.trainmepractice.R
import com.pedro.library.AutoPermissions

class Q5_trainer : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q5_trainer)

        val bundle = intent.extras!!
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지

        AutoPermissions.loadAllPermissions(this, 101)

        val seekbar5 = findViewById<VerticalSeekBar>(R.id.seekbar5)
        seekbar5.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return true
            }
        })

        db_realtime = Firebase.database.reference
        findViewById<FloatingActionButton>(R.id.nextButton5).setOnClickListener {
            val programName = findViewById<EditText>(R.id.programNameEditText).text.toString()
            val trainerMajor = findViewById<EditText>(R.id.trainerMasterEditText).text.toString()
            val program_additional = findViewById<EditText>(R.id.trainerAdditionalEditText).text.toString()

            if(programName.length > 0){
                if(trainerMajor.length > 0){
                    val intent = Intent(this, Q6_Trainer::class.java)
                    bundle.putString("programName",programName)
                    bundle.putString("trainerMajor",trainerMajor)
                    bundle.putString("program_additional",program_additional)
                    intent.putExtras(bundle)

                    startActivity(intent)
                } else{
                    Toast.makeText(this, "트레이너님의 주 종목을 말씀해주세요",Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(this, "프로그램 이름을 입력해 주세요", Toast.LENGTH_LONG).show()
            }
        }
        findViewById<Button>(R.id.q5_skipButton).setOnClickListener {
            val intent = Intent(this, Q6_Trainer::class.java)
            bundle.putString("programName","-")
            bundle.putString("trainerMajor","-")
            bundle.putString("program_additional","-")
            intent.putExtras(bundle)

            startActivity(intent)
        }
    }
}
