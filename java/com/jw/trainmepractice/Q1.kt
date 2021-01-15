package com.jw.trainmepractice

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// 유저의 질문을 받는 화면

class Q1 : AppCompatActivity() {

    lateinit var db_realtime: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.q1)

        db_realtime = Firebase.database.reference

        // 스피너 구성(여러개 중 하나 선택하는 부분) 부분인데
        // age나 where 정보를 받을 때 어떻게 만들지에 대한 생각이 필요할거같음
        // age의 기준을 뭐 60대이상으로 할지 아님 70대 이상으로 할지 등등을 정해야 하고
        // 지역같은 경우 전국에 도시가 너무 많아서 그거에 대한 방법 필요
        // 원래는 지역마다 list를 다 만들어 줘서 도시를 선택하면 그에 따른 구? (예를 들어 서울을 선택하면 강남/강동/...이 나오게) 를 스피너에 넣어줄라 했는데
        // 아까 다른 어플에서는 어떻게 하는지 보여준대놓고 안보여줌
        // 이 글을 보고있다면 나에게 알려줘....

        val sex_spinner = findViewById<Spinner>(R.id.sex_spinner)
        val age_spinner = findViewById<Spinner>(R.id.age_spinner)
        val where_spinner = findViewById<Spinner>(R.id.where_spinner)

        val array_sex = listOf("남자", "여자")
        val array_age = listOf("10대 미만", "10대", "20대", "30대", "40대", "50대", "60대 이상")
        val array_where = listOf("서울", "대구", "부산", "울산")

        sex_spinner.adapter = getAdapter(this, array_sex)
        age_spinner.adapter = getAdapter(this, array_age)
        where_spinner.adapter = getAdapter(this, array_where)


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

                val sp = getSharedPreferences("myInfo", Activity.MODE_PRIVATE)
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