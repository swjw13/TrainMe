package com.jw.trainmepractice.userMainFragment


import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.ChatRoom
import com.jw.trainmepractice.R
import com.jw.trainmepractice.memberClass.Trainer

class fragment_3(val fragment: FragmentActivity) : Fragment() {
    lateinit var db_realtime: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_3_chat, container, false)

        val list = arrayListOf<Trainer>()
        val recyclerview = view.findViewById<RecyclerView>(R.id.chatlist_recyclerview)
        val manager = LinearLayoutManager(activity)
        val adapter = recyclerAdapter(
            list,
            inflater,
            fragment
        )

        recyclerview.adapter = adapter
        recyclerview.layoutManager = manager

        val token = fragment.getSharedPreferences("user",MODE_PRIVATE).getString("login_token","null")
        db_realtime = Firebase.database.reference
        db_realtime.child("user").child(token!!).child("matchedTrainer").addChildEventListener(object: ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.update(snapshot.getValue(Trainer::class.java)!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                adapter.remove(snapshot.getValue(Trainer::class.java)!!)
                adapter.notifyDataSetChanged()
            }
        })

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
        val trainer_img = itemview.findViewById<ImageView>(R.id.TrainerImg_chatlist)
        val card = itemview.findViewById<CardView>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.fragment_3_chat_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(list[position].name)
        holder.card.setOnClickListener {
            val intent =  Intent(fragment, ChatRoom::class.java)
            intent.putExtra("trainerName",list[position].name)
            intent.putExtra("FCMToken",list[position].FCMToken)
            intent.putExtra("trainerId", list[position].id)
            startActivity(fragment,intent,null)
        }
        holder.trainer_img.setOnClickListener {
            val proposal = Intent(fragment, SeeProposal::class.java)
            proposal.putExtra("trainerId", list[position].id)
            proposal.putExtra("trainerName", list[position].name)
            fragment.startActivity(proposal)
        }
    }
    fun update(item: Trainer){
        list.add(item)
    }
    fun remove(item: Trainer){
        list.remove(item)
    }
}