package com.jw.trainmepractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase



class Q2 : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q2)

        db_realtime = Firebase.database.reference

        findViewById<Button>(R.id.q2_nextbutton).setOnClickListener {
            val height = findViewById<EditText>(R.id.ans_height).text.toString()
            val weight = findViewById<EditText>(R.id.ans_weight).getText().toString()
            val weightprofile = findViewById<EditText>(R.id.ans_weightprofile).getText().toString()

            if((height.length != 0) && (weight.length != 0) && (weightprofile.length != 0)){

                val real = db_realtime.child("user").child(Firebase.auth.currentUser!!.uid)
                real.child("height").setValue(height)
                real.child("weight").setValue(weight)
                real.child("weightprofile").setValue(weightprofile)

                val sp = getSharedPreferences("myInfo", Activity.MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("height", height)
                editor.putString("weight", weight)
                editor.putString("weightprofile", weightprofile)
                editor.apply()

                startActivity(Intent(this, Q3::class.java))
            } else {
                makeToast(this,"정보를 입력해주세요!" )
            }
        }
    }
}