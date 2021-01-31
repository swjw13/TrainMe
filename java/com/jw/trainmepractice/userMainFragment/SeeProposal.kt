package com.jw.trainmepractice.userMainFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jw.trainmepractice.ChatRoom
import com.jw.trainmepractice.R
import com.jw.trainmepractice.memberClass.Trainer


class SeeProposal : AppCompatActivity() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference = firebaseDatabase.reference

    private var trainerID: String? = null
    private var trainerName: String? = null

    lateinit var proposal_trainerName: TextView
    lateinit var proposal_trainerMainPosition: TextView
    lateinit var proposal_trainerProgram: TextView
    lateinit var proposal_trainerAvailableTime: TextView
    lateinit var proposal_trainerPrice: TextView
    lateinit var proposal_trainerMedals: TextView
    lateinit var proposal_trainerSNS: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_proposal)

        InitializeView()

        val intent = intent
        trainerID = intent.getStringExtra("trainerId")
        trainerName = intent.getStringExtra("trainerName")

        SetText(trainerID)

        findViewById<Button>(R.id.gotoChatroomButton).setOnClickListener {
            val userId = getSharedPreferences("user", Context.MODE_PRIVATE).getString("login_token","null")

            val intent_toChatroom = Intent(this, ChatRoom::class.java)
            intent_toChatroom.putExtra("trainerName", trainerName)
            databaseReference.child("trainer").child(trainerID!!).child("profile").addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    databaseReference.child("user").child(userId!!).child("matchedTrainer").child(trainerID!!).setValue(snapshot.getValue(Trainer::class.java))
                    intent_toChatroom.putExtra("FCMToken", snapshot.getValue(Trainer::class.java)?.FCMToken)
                }
            })
            intent_toChatroom.putExtra("trainerId", trainerID)
            startActivity(intent_toChatroom)
            finish()
        }

        findViewById<LinearLayout>(R.id.gotoReview).setOnClickListener{
            val intent = Intent(this@SeeProposal, CommentRoom::class.java)
            intent.putExtra("trainerId",trainerID)
            startActivity(intent)
        }

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    fun SetText(trainerID: String?) {
        databaseReference.child("trainer").child(trainerID!!).child("profile").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val trainer = snapshot.getValue(Trainer::class.java)!!
                proposal_trainerName.setText(trainer.name)
                proposal_trainerMainPosition.setText(trainer.mainPosition)
                proposal_trainerProgram.setText(trainer.programName + "\n" + trainer.programAdditional)
                proposal_trainerAvailableTime.setText(trainer.availableTime)
                proposal_trainerPrice.setText("10회: " + trainer.price10 + "\n 20회: " + trainer.price20)
                proposal_trainerSNS.setText(trainer.sns)
                proposal_trainerMedals.setText(trainer.trainerMajor)
            }
        })
    }


    fun InitializeView() {
        proposal_trainerMedals = findViewById(R.id.proposal_trainerMedals)
        proposal_trainerName = findViewById(R.id.proposal_trainerName)
        proposal_trainerMainPosition = findViewById(R.id.proposal_trainerMainPosition)
        proposal_trainerProgram = findViewById(R.id.proposal_trainerProgram)
        proposal_trainerAvailableTime = findViewById(R.id.proposal_trainerAvailableTime)
        proposal_trainerPrice = findViewById(R.id.proposal_trainerPrice)
        proposal_trainerSNS = findViewById(R.id.proposal_trainerSNS)
    }
}