package com.jw.trainmepractice


import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// 탭의 3번째 화면(채팅 부분)을 구성하는 fragment
// 일단은 11명의 객체를 넣어둠
// 이 부분도 앞의 탭들과 비슷하게 Trainer객체의 형태를 확정해 주고 list에 넣을 형태를 확정시켜야 완성 할 수 있을거 같음
// 이부분엔 아직 Database Listener를 안달아줬음

class fragment_3(val fragment: FragmentActivity) : Fragment() {
    lateinit var db_realtime: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3_chat, container, false)

        val list = arrayListOf<Trainer>()
        for(i in 0..10){
            list.add(Trainer(""+i,"남",""+(i+20),""+(i+1000)))
        }
        val recyclerview = view.findViewById<RecyclerView>(R.id.chatlist_recyclerview)
        val manager = LinearLayoutManager(getActivity())
        val adapter = recyclerAdapter(list, inflater, fragment)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = manager


        return view
    }
}

class recyclerAdapter(
        var list: ArrayList<Trainer>,
        val inflater: LayoutInflater,
        val fragment: FragmentActivity
): RecyclerView.Adapter<recyclerAdapter.ViewHolder>(){
    inner class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val name = itemview.findViewById<TextView>(R.id.TrainerName_chatList)
        val chatIcon = itemview.findViewById<ImageButton>(R.id.chatIcon)
        val trainer_img = itemview.findViewById<ImageView>(R.id.TrainerImg_chatlist)
        val card = itemview.findViewById<CardView>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.fragment_3_chat_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: recyclerAdapter.ViewHolder, position: Int) {
        holder.name.setText(list[position].name)
        holder.chatIcon.setOnClickListener {
            val intent =  Intent(fragment, ChatRoom::class.java)
            intent.putExtra("trainerName",list[position].name)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(fragment,intent,null)
        }
        holder.card.setOnClickListener {
            val intent =  Intent(fragment, ChatRoom::class.java)
            intent.putExtra("trainerName",list[position].name)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(fragment,intent,null)
        }
        holder.trainer_img.setOnClickListener {
            CustomDialog(fragment).callFunction()
        }
    }
    fun update(item: Trainer){
        list.add(item)
    }
}