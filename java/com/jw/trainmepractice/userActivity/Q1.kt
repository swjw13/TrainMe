package com.jw.trainmepractice.userActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.getAdapter
import com.jw.trainmepractice.makeToast

// 유저의 질문을 받는 화면

class Q1 : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1)

        db_realtime = Firebase.database.reference

        val sex_spinner = findViewById<Spinner>(R.id.sex_spinner)
        val age_spinner = findViewById<Spinner>(R.id.age_spinner)
        val where_spinner = findViewById<Spinner>(R.id.where_spinner)

        val array_sex = listOf("남자", "여자")
        val array_age = listOf("10대 미만", "10대", "20대", "30대", "40대", "50대", "60대 이상")
        val array_where = listOf("서울", "대구", "부산", "울산")

        sex_spinner.adapter = getAdapter(this, array_sex)
        age_spinner.adapter = getAdapter(this, array_age)
        where_spinner.adapter =
            getAdapter(this, array_where)


        findViewById<Button>(R.id.q1_nextbutton).setOnClickListener {

            val sex = sex_spinner.selectedItem.toString()
            val age = age_spinner.selectedItem.toString()
            val where = where_spinner.selectedItem.toString()
            val name = findViewById<EditText>(R.id.ans_name).text.toString()


            // 지금은 그냥 입력을 안 했을 때만 걸러주고 있는데
            // 추후에 필수질문 / 선택질문 구분한다면 조건을 추가해 줘야 할 거 같음
            // 예시로 editText에서 글자 하나하나를 체크하는 리스너가 있더라고요?
            // 이용을 하면 될거같기도 한데 모르겟다

            if (sex.length != 0 && age.length != 0 && where.length != 0) {

                val realtimeDB = db_realtime.child("user").child(Firebase.auth.currentUser!!.uid)
                realtimeDB.child("name").setValue(name)
                realtimeDB.child("sex").setValue(sex)
                realtimeDB.child("age").setValue(age)
                realtimeDB.child("where").setValue(where)
                realtimeDB.child("userID").setValue(Firebase.auth.currentUser!!.uid)


                // 혹시 몰라서 데이터를 sp에 저장함
                // 나는 chatRoom에서 list에 넣는 객체에서 내 이름 부분을 이걸로 처리했는데
                // 따로 이용해도 좋을거같음!

                val sp = getSharedPreferences("user", Activity.MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("name", name)
                editor.putString("sex", sex)
                editor.putString("age", age)
                editor.putString("where", where)
                editor.putString("userID", Firebase.auth.currentUser!!.uid)
                editor.apply()

                startActivity(Intent(this, Q2::class.java))
                // 화면을 바꿀 때 이전 기록을 없애는 기술 필요
            } else {
                makeToast(this, "정보를 입력해주세요!")
            }


        }
    }

}