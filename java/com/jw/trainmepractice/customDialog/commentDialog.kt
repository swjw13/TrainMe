package com.jw.trainmepractice.customDialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.memberClass.Comment
import com.jw.trainmepractice.memberClass.User

class commentDialog(val context: Context, val trainerId: String?){

    var dig = Dialog(context)

    fun callFunction() {
        // 액티비티의 타이틀바를 숨긴다
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.setContentView(R.layout.commentalert)

        dig.show()
        dig.findViewById<Button>(R.id.commentSubmit).setOnClickListener {
            val database = Firebase.database.reference
            val token = context.getSharedPreferences("user",Context.MODE_PRIVATE).getString("login_token","null")

            val rating = dig.findViewById<RatingBar>(R.id.commentRating).rating
            val text = dig.findViewById<EditText>(R.id.commentBody).text.toString()

            database.child("user").child(token!!).child("profile").addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "작성에 실패하셨습니다",Toast.LENGTH_LONG).show()
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.getValue(User::class.java)?.name
                    database.child("trainer").child(trainerId!!).child("comments").push().setValue(Comment(jaksungja = name, text = text, rating = rating.toString()))
                    Toast.makeText(context, "작성에 성공하셨습니다",Toast.LENGTH_LONG).show()
                }
            })

            dig.dismiss()
        }
        dig.findViewById<Button>(R.id.commentCancel).setOnClickListener {
            Toast.makeText(context, "작성을 취소하셨습니다.", Toast.LENGTH_LONG).show()
            dig.dismiss()
        }
    }
}