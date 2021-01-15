package com.jw.trainmepractice

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

// 우리의 메인 화면, TAb에서 첫 번째 화면을 담당.

class fragment_1(val fragment: FragmentActivity) : Fragment() {

    lateinit var db_realtime: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1_main, container, false)

        db_realtime = Firebase.database.reference


        // 유저의 개인 프로필을 만드는 부분
        // 일단은 user의 name, age, where만 실시간으로 받아와서 업데이트를 해 주도록 구성했는데(TextView를 3개 만들어 놓음)
        // 추후에 화면 구성을 확정했을 때 다시 만들면 될거 같고
        // 원래는 이 fragment_1 위에 고정을 해 뒀는데, 비회원/회원 구분을 통해 화면을 바꿔준다 그러면
        // 이 부분을 빈 Layout으로 만들어 두고 따로 fragment로 만들어서 조건에 따라 (비회원을 위한 화면) <---> (회원을 위한 화면: 개인 정보가 열린 화면 ) 으로 바꿔 주면 좋을 것 같음

        view.findViewById<FloatingActionButton>(R.id.Button_to_changeInfo).setOnClickListener {
            startActivity(Intent(fragment, UserMainProfile::class.java))
        }
        db_realtime.child("user").child(Firebase.auth.currentUser!!.uid).child("name").addValueEventListener(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) { }
                override fun onDataChange(snapshot: DataSnapshot) {
                    view.findViewById<TextView>(R.id.username_main).setText(snapshot.value.toString())
                }
            })
        db_realtime.child("user").child(Firebase.auth.currentUser!!.uid).child("age").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) { }
            override fun onDataChange(snapshot: DataSnapshot) {
                view.findViewById<TextView>(R.id.userage_main).setText(snapshot.value.toString())
            }
        })
        db_realtime.child("user").child(Firebase.auth.currentUser!!.uid).child("where").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) { }
            override fun onDataChange(snapshot: DataSnapshot) {
                view.findViewById<TextView>(R.id.userwhere_main).setText(snapshot.value.toString())
            }
        })



        // 리스트를 만들어 주는 부분
        // 뷰는 recyclerview를 사용했고
        // 데이터가 없어서 일단 11개의 Trainer 객체를 넣어놓고 리스트로 보여주고 있음
        // 추후에 해야 할 것
        //      - 리스트에 넣어 줄 Trainer 객체 형태 확정: 지금은 Trainer 객체에 name, sex, age, userid만 들어가 있는데 DB에 넣어 줄 트레이너 정보 전부를 넣어도 되고 일부분만 넣어서 만들어도 되는데
        //        형태의 변화는 필요 할 거 같음
        //      - 실시간 업데이트를 하기 위해서는 adapter를 변형하거나 list를 추가하고 adapter.notifDataSetChanged를 해 줘야 하는데, 당장 이해 할 필요는 없고 어떤 기능인지 궁금하면 ChatRoom.kt 확인요망

        val list = ArrayList<Trainer>()
        for (i in 0..10) {
            list.add(Trainer("보노보노", "남", "" + (i + 10), "" + (i + 10000)))
        }
        val recyclerview = view.findViewById<RecyclerView>(R.id.main1_list)
        val manager = LinearLayoutManager(getActivity())
        val adapter = fragment1_adapter(list, inflater, fragment)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = manager

        return view
    }
}

class fragment1_adapter(
    var list: ArrayList<Trainer>,
    val inflater: LayoutInflater,
    val fragment: FragmentActivity
) : RecyclerView.Adapter<fragment1_adapter.ViewHolder>() {
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val image = itemview.findViewById<ImageView>(R.id.main_trainer_image)
        val userbox = itemview.findViewById<LinearLayout>(R.id.trainerbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.fragment_1_main_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setOnClickListener {
            holder.image.setOnClickListener {
                CustomDialog(fragment).callFunction()
            }
        }
        holder.userbox.setOnClickListener {
            CustomDialog(fragment).callFunction()
        }
    }
}