package com.jw.trainmepractice.userMainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jw.trainmepractice.R
import com.jw.trainmepractice.customDialog.commentDialog
import com.jw.trainmepractice.memberClass.Comment

class CommentRoom : AppCompatActivity() {

    lateinit private var adapter: commentAdapter
    lateinit private var rView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_room)

        val database = Firebase.database.reference
        val trainerId = intent.getStringExtra("trainerId")!!

        findViewById<Button>(R.id.gotoMakeComment).setOnClickListener {
            commentDialog(this, trainerId).callFunction()
        }

        adapter = commentAdapter(arrayListOf(), LayoutInflater.from(this))
        rView = findViewById(R.id.commentRoomRecyclerview)
        rView.adapter = adapter
        rView.layoutManager = LinearLayoutManager(this)

        database.child("trainer").child(trainerId).child("comments").addChildEventListener(object: ChildEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.add(snapshot.getValue(Comment::class.java)!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                adapter.delete(snapshot.getValue(Comment::class.java)!!)
                adapter.notifyDataSetChanged()
            }
        })

    }
}

private class commentAdapter(
    var list: ArrayList<Comment>,
    val inflater: LayoutInflater
): RecyclerView.Adapter<commentAdapter.ViewHolder>(){

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val commentName = itemview.findViewById<TextView>(R.id.commentName)
        val commentRating = itemview.findViewById<RatingBar>(R.id.commentRating)
        val commentText = itemview.findViewById<TextView>(R.id.commentText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = inflater.inflate(R.layout.commentitem, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commentName.setText(list[position].jaksungja)
        holder.commentRating.rating = list[position].rating!!.toFloat()
        holder.commentText.setText(list[position].text)
    }
    fun add(item: Comment){
        list.add(item)
    }
    fun delete(item: Comment){
        list.remove(item)
    }
}