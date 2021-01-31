package com.jw.trainmepractice.customDialog

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.makeToast

// 개인 정보 화면에서 데이터를 변경할 때 나오는 dialog를 정리한 부분.
// 이거를 그냥 editText랑 버튼만 넣어놨는데
// 항목에 따라 spinner를 넣던지, 아니면 editText를 넣던지, 데이터를 추가할지 말지 정해야 함

class userinfo_dialog(val context: Context) {

    val db_realtime = Firebase.database.reference
    var dig = Dialog(context)
    fun callFunction(view: TextView, question: String, sp: SharedPreferences) {
        // 액티비티의 타이틀바를 숨긴다
        dig.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dig.setContentView(R.layout.userinfo_alert)
        dig.show()

        dig.findViewById<Button>(R.id.userinfo_changeButton).setOnClickListener {
            val new_thing = dig.findViewById<EditText>(R.id.ChangeItem).text.toString()
            view.setText(new_thing)

            db_realtime
                .child("user")
                .child(Firebase.auth.currentUser!!.uid)
                .child(question)
                .setValue(new_thing)

            makeToast(context, "정보가 변경되었습니다! 재접속 시 적용됩니다")

            sp.edit().putString(question, new_thing)

            dig.dismiss()
        }
        dig.findViewById<Button>(R.id.userinfo_cancelButton).setOnClickListener {
            dig.dismiss()
        }
    }
}
