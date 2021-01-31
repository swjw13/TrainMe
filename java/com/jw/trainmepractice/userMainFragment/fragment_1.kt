package com.jw.trainmepractice.userMainFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.UserMainProfile
import com.jw.trainmepractice.memberClass.Proposal
import com.jw.trainmepractice.memberClass.Trainer
import com.jw.trainmepractice.userActivity.Login

class fragment_1(val fragment: FragmentActivity) : Fragment() {

    lateinit private var itemview: View
    lateinit var db_realtime: DatabaseReference
    lateinit var howMany: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        itemview = inflater.inflate(R.layout.fragment_1_main, container, false)
        howMany = itemview.findViewById(R.id.frag1_howMany)

        var list = arrayListOf<String>()
        db_realtime = Firebase.database.reference
        val user_token = fragment.getSharedPreferences("user", Context.MODE_PRIVATE).getString("login_token", "null")

        val recyclerview = itemview.findViewById<RecyclerView>(R.id.main1_list)
        val adapter = fragment1_adapter(arrayListOf(), LayoutInflater.from(fragment), fragment, db_realtime)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(fragment, LinearLayoutManager.HORIZONTAL, false)

        db_realtime.child("user")
                .child(user_token!!)
                .child("fromTrainer").addChildEventListener(object : ChildEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }
                    override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    }

                    override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    }

                    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                        adapter.update(snapshot.getValue(Proposal::class.java)!!)
                        adapter.notifyDataSetChanged()

                        list.add("1")
                        howMany.setText("회원님의 문의에 대한\n전문가 " + list.size + "명의 \n답변이 도착했습니다")
                    }

                    override fun onChildRemoved(snapshot: DataSnapshot) {
                        adapter.remove(snapshot.getValue(Proposal::class.java)!!)
                        adapter.notifyDataSetChanged()
                    }
                })

        val sp = fragment.getSharedPreferences("user", Context.MODE_PRIVATE)
        val login_token = sp.getString("login_token", "null")
        if (login_token == null || login_token == "null") {
            itemview.findViewById<RelativeLayout>(R.id.con1).visibility = GONE
            itemview.findViewById<LinearLayout>(R.id.con2).visibility = VISIBLE

            itemview.findViewById<LinearLayout>(R.id.con2).setOnClickListener{
                val intent = Intent(fragment, Login::class.java)
                startActivity(intent)
            }
        } else {
            itemview.findViewById<RelativeLayout>(R.id.con1).visibility = VISIBLE
            itemview.findViewById<LinearLayout>(R.id.con2).visibility = GONE
        }

        itemview.findViewById<FloatingActionButton>(R.id.frag1_user_toProfile).setOnClickListener {
            val intent = Intent(fragment, UserMainProfile::class.java)
            startActivity(intent)
        }

        return itemview
    }

    override fun onResume() {
        super.onResume()
        val sp = fragment.getSharedPreferences("user", Context.MODE_PRIVATE)
        val login_token = sp.getString("login_token", "null")
        if (login_token == null || login_token == "null") {
            itemview.findViewById<RelativeLayout>(R.id.con1).visibility = GONE
            itemview.findViewById<LinearLayout>(R.id.con2).visibility = VISIBLE
        } else {
            itemview.findViewById<RelativeLayout>(R.id.con1).visibility = VISIBLE
            itemview.findViewById<LinearLayout>(R.id.con2).visibility = GONE
        }
    }
}

class fragment1_adapter(
        var list: ArrayList<Proposal>,
        val inflater: LayoutInflater,
        val fragment: FragmentActivity,
        val database: DatabaseReference
) : RecyclerView.Adapter<fragment1_adapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val name = itemview.findViewById<TextView>(R.id.frag1_trainer_name)
        val position = itemview.findViewById<TextView>(R.id.frag1_trainer_position)
        val price = itemview.findViewById<TextView>(R.id.frag1_trainer_price)
        val oneLineInfo = itemview.findViewById<TextView>(R.id.frag1_trainer_oneLineinfo)
        val image = itemview.findViewById<ImageView>(R.id.frag1_trainer_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.frag1_user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: fragment1_adapter.ViewHolder, position: Int) {

        val trainerId = list[position].id

        Firebase.database.reference.child("trainer")
                .child(trainerId!!)
                .child("profile").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val trainer = snapshot.getValue(Trainer::class.java)
                        holder.name.setText(trainer?.name + " ( " + trainer?.sex + ")")
                        holder.position.setText(trainer?.mainPosition)
                        holder.price.setText(trainer?.price10 + "원 / " + "10회")
                        holder.oneLineInfo.setText(trainer?.oneLineInfo)
                    }
                })

        holder.image.setOnClickListener {
            val intent = Intent(fragment, UserPopup::class.java)
            val trainerName = list[position].name
            intent.putExtra("price", list[position].price)
            intent.putExtra("message", list[position].message)
            intent.putExtra("perweek", list[position].perWeek);
            intent.putExtra("total", list[position].total);
            intent.putExtra("trainerName", trainerName)
            intent.putExtra("trainerId", trainerId)
            fragment.startActivity(intent)
        }
    }

    fun update(item: Proposal) {
        list.add(item)
    }

    fun remove(item: Proposal) {
        list.remove(item)
    }
}

// 화면 위에 뜨는 팝업 형식으로 나오는 방법
//holder.image.setOnClickListener {
//    holder.image.setOnClickListener {
//        CustomDialog(fragment).callFunction()
//    }
//}
//holder.userbox.setOnClickListener {
//    CustomDialog(fragment).callFunction()
//}