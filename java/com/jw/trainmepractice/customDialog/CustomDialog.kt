package com.jw.trainmepractice.customDialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R

// Alert를 위한 class
// 추후에 트레이너의 상세 프로필 보기 등등을 위한 객체를 비슷하게 만들어 줘야 할 거 같다

class CustomDialog(val context: Context) {

    var dig = Dialog(context)

    fun callFunction() {
        // 액티비티의 타이틀바를 숨긴다
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.setContentView(R.layout.commentalert)

        dig.show()

        dig.findViewById<Button>(R.id.commentSubmit).setOnClickListener {
            val database = Firebase.database.reference

            val rating = dig.findViewById<RatingBar>(R.id.commentRating)

            dig.dismiss()
        }
        dig.findViewById<Button>(R.id.commentCancel).setOnClickListener {
            Toast.makeText(context, "보노보노시러", Toast.LENGTH_LONG).show()
            dig.dismiss()
        }
    }

}