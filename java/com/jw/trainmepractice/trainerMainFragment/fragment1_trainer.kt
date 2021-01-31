package com.jw.trainmepractice.trainerMainFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.jw.trainmepractice.R
import com.jw.trainmepractice.memberClass.Proposal

class fragment1_trainer(val fragment: FragmentActivity) : Fragment() {

    lateinit var db_realtime: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment1_trainer, container, false)

        db_realtime = Firebase.database.reference

        val recyclerview = view.findViewById<RecyclerView>(R.id.frag1_trainer_recyclerView)
        val adapter = frag1Adapter(arrayListOf(), LayoutInflater.from(fragment), fragment)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(fragment)

        val user_token = fragment.getSharedPreferences("trainer", Context.MODE_PRIVATE).getString("login_token", "null")

        db_realtime.child("trainer").child(user_token!!).child("toUser").addChildEventListener(object: ChildEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.update(snapshot.getValue(Proposal::class.java)!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                adapter.remove(snapshot.getValue(Proposal::class.java)!!)
                adapter.notifyDataSetChanged()
            }
        })


        return view
    }
}

class frag1Adapter(
        var list: ArrayList<Proposal>,
        val inflater: LayoutInflater,
        val fragment: FragmentActivity
): RecyclerView.Adapter<frag1Adapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.frag1_trainer_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
    fun update(item: Proposal) {
        list.add(item)
    }
    fun remove(item: Proposal){
        list.remove(item)
    }
}