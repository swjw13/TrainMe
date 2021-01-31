package com.jw.trainmepractice.userMainFragment

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R

class frag2_trainerProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frag2_trainer_profile)

        val trainerId = intent.getStringExtra("trainerId")!!

        findViewById<Button>(R.id.sendProposalButton).setOnClickListener {
            // 유저가 트레이너에게 요청을 보내는 코드
        }

        val db_realtime = Firebase.database.reference
        db_realtime.child("trainer").child(trainerId).child("profile").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                // 뷰에다가 트레이너의 정보를 입력하는 코드
            }
        })
    }
}