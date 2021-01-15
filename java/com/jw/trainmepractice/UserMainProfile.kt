package com.jw.trainmepractice

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// 유저의 메인 프로필을 정리 한 부분
// 나머지는 그냥 그렇게 보면 되고
// 나는 정보들을 그냥 recyclerview로 모든 부분을 업데이트하는 방법을 썼는데
// 이걸 그냥 Text로 화면에 박아두는게 좋은지 모르겠다

class UserMainProfile : AppCompatActivity() {

    lateinit var sp: SharedPreferences
    private lateinit var db_realtime: DatabaseReference
    private lateinit var thisadapter: MainInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_profile)

        sp = getSharedPreferences("myInfo", Activity.MODE_PRIVATE)

        db_realtime = Firebase.database.reference

        val view = findViewById<RecyclerView>(R.id.userinfo_list)
        thisadapter = MainInfoAdapter(LayoutInflater.from(this), this, sp)
        view.adapter = thisadapter
        view.layoutManager = LinearLayoutManager(this)

        val samedb = db_realtime.child("user").child(Firebase.auth.currentUser!!.uid)

        getval("name")
        getval("age")
        getval("sex")
        getval("height")
        getval("weight")
        getval("weightprofile")
        getval("userBodyInfo")
        getval("userEatHabit")
        getval("userSportAvailable")
        getval("time")
        getval("goal")
        getval("extra")

        val actionbar = supportActionBar
        actionbar?.setTitle("나의 정보")
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
    fun getval(question: String){
        val sb = db_realtime.child("user").child(Firebase.auth.currentUser!!.uid)
        sb.child(question).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                thisadapter.update(UserProfile(question,snapshot.value.toString()))
                thisadapter.notifyDataSetChanged()
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val curId = item.itemId
        when (curId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

private class UserProfile(
    val question: String,
    val answer: String
)

private class MainInfoAdapter(
//    var list: ArrayList<UserProfile>,
    val inflater: LayoutInflater,
    val context: Context,
    val sp: SharedPreferences
) : RecyclerView.Adapter<MainInfoAdapter.ViewHolder>() {

    var list = ArrayList<UserProfile>()

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val question = itemview.findViewById<TextView>(R.id.mainInfo_question)
        val answer = itemview.findViewById<TextView>(R.id.mainInfo_answer)
        val layout = itemview.findViewById<ConstraintLayout>(R.id.userprofileall)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = inflater.inflate(R.layout.mainprofile_listitem, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.layout.setOnClickListener {
            userinfo_dialog(context).callFunction(holder.answer, list[position].question, sp)
        }
        holder.question.setText(list[position].question)
        holder.answer.setText(list[position].answer)
    }

    fun update(item: UserProfile) {
        list.add(item)
    }
}