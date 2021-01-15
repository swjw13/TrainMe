package com.jw.trainmepractice

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

// 채팅을 위한 화면 구성
// 채팅 방 이름은 트레이너 이름 + 유저의 UID값으로 설정했는 데 추후에 바꿀 수는 있을거 같다.
// fragment_3에서 trainerName 정보를 담은 intent를 받아 온다

class ChatRoom : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var adapter: ChatViewAdapter
    lateinit var view: RecyclerView
    lateinit var Editview: EditText
    lateinit var roomName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room)

        val trainerName = intent.getStringExtra("trainerName")!!

        var list = ArrayList<Chats>()
        view = findViewById(R.id.Chats)
        adapter = ChatViewAdapter(list,LayoutInflater.from(this))
        view.adapter = adapter
        view.layoutManager = LinearLayoutManager(this)

        roomName = trainerName + Firebase.auth.currentUser?.uid
        database = Firebase.database.reference


        // 채팅을 입력하는 부분

        findViewById<Button>(R.id.sendButton).setOnClickListener {
            val calendar: Calendar = Calendar.getInstance() //현재 시간을 가지고 있는 객체
            val time: String = calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + calendar.get(Calendar.MINUTE) //14:16

            Editview = findViewById(R.id.inputEditText)
            val text = Editview.text.toString()
            val my_name = getSharedPreferences("myInfo",Activity.MODE_PRIVATE).getString("name","None")!!

            database.child("chats").child(roomName).push().setValue(Chats(my_name, text, time, Firebase.auth.currentUser?.uid))
            Editview.setText("")
        }

        // 리스트를 실시간으로 받아오는 부분

        database.child("chats").child(roomName).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(error: DatabaseError) { }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) { }
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.update(snapshot.getValue(Chats::class.java)!!)
                adapter.notifyDataSetChanged()
                view.scrollToPosition(list.size - 1)
            }
            override fun onChildRemoved(snapshot: DataSnapshot) { }
        })

        // 상단바(뒤로가기, 신고버튼)

        val actionbar = supportActionBar
        actionbar?.setTitle(trainerName)
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chatroom_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val curId = item.itemId
        when(curId){
            android.R.id.home ->{
                finish()
                return true
            }
            R.id.alert -> Toast.makeText(this,"신고해야합니다!",Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }
}

class ChatViewAdapter(
    var list: ArrayList<Chats>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<ChatViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val others = itemview.findViewById<RelativeLayout>(R.id.others)
        val other_name = itemview.findViewById<TextView>(R.id.other_name)
        val other_text = itemview.findViewById<TextView>(R.id.other_msg)
        val other_time = itemview.findViewById<TextView>(R.id.other_time)
        val other_img = itemview.findViewById<CircleImageView>(R.id.other_img)
        val mine = itemview.findViewById<RelativeLayout>(R.id.mine)
        val my_name = itemview.findViewById<TextView>(R.id.my_name)
        val my_text = itemview.findViewById<TextView>(R.id.my_text)
        val my_time = itemview.findViewById<TextView>(R.id.my_time)
        val my_img = itemview.findViewById<CircleImageView>(R.id.my_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = inflater.inflate(R.layout.chatroom_item, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(list[position].userid == Firebase.auth.currentUser?.uid){
            holder.others.setVisibility(View.GONE)
            holder.mine.setVisibility(View.VISIBLE)
            holder.my_name.setText(list[position].user)
            holder.my_text.setText(list[position].text)
            holder.my_time.setText(list[position].time)
            //holder.my_img.
        } else{
            holder.others.setVisibility(View.VISIBLE)
            holder.mine.setVisibility(View.GONE)
            holder.other_name.setText(list[position].user)
            holder.other_text.setText(list[position].text)
            holder.other_time.setText(list[position].time)
        }
    }

    fun update(item: Chats) {
        list.add(item)
    }
}


// 채팅을 입력할 때 list에 올려주는 객체. 더 많은 기능이 필요할 경우 이 부분을 업데이트 한 후
// 채팅 입력 부분만 고쳐주면 될거같다
class Chats(
    var user: String? = null,
    var text: String? = null,
    var time: String? = null,
    var userid: String? = null
)