package com.jw.trainmepractice

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatRoom : AppCompatActivity() {
    lateinit var database: DatabaseReference
    lateinit var adapter: ChatViewAdapter
    lateinit var view: RecyclerView
    lateinit var Editview: EditText
    lateinit var roomName: String
    var requestQueue: RequestQueue? = null
    lateinit var regId: String
    lateinit var trainerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN) // 키보드가 화면을 가리는 걸 방지
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(applicationContext)
        }

        val trainerName = intent.getStringExtra("trainerName")!!
        regId = intent.getStringExtra("FCMToken")!!
        trainerId = intent.getStringExtra("trainerId")!!
        val myId = getSharedPreferences("user", Context.MODE_PRIVATE).getString("login_token","null")

        // recyclerview 기본 셋팅
        var list = ArrayList<Chats>()
        view = findViewById(R.id.Chats)
        adapter = ChatViewAdapter(list,LayoutInflater.from(this))
        view.adapter = adapter
        view.layoutManager = LinearLayoutManager(this)

        // 채팅방을 구분하는 이름은 유저ID + 트레이너ID
        roomName = myId + trainerId
        database = Firebase.database.reference


        // 채팅을 입력하는 부분
        findViewById<Button>(R.id.sendButton).setOnClickListener {
            Editview = findViewById(R.id.inputEditText)
            val text = Editview.text.toString()
            if(text.length > 0){
                val calendar: Calendar = Calendar.getInstance() //현재 시간을 가지고 있는 객체
                val time: String = calendar.get(Calendar.MONTH).toString() + "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + calendar.get(Calendar.MINUTE)

                val my_name = getSharedPreferences("user",Activity.MODE_PRIVATE).getString("name","None")!!
                database.child("chats").child(roomName).push().setValue(Chats(my_name, text, time, myId))
                Editview.setText("")

                // fcm을 사용하는 코드
                // 제대로 동작하는지는 미지수
                send(my_name, text)
            } else{
                makeToast(this,"문자를 입력하세요")
            }
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
    private fun send(text: String, from: String){
        val requestData = JSONObject()
        try{
            requestData.put("priority","high")
            val dataObj = JSONObject()
            dataObj.put("text",text)
            dataObj.put("from",from)
            requestData.put("data",dataObj)

            val idArray = JSONArray()
            idArray.put(0,regId)
            requestData.put("registration_ids",idArray)
        } catch (e: Exception){
            e.printStackTrace()
        }

        sendData(requestData, object: SendResponseListener{
            override fun onRequestStarted() {
            }

            override fun onRequestCompleted() {
            }

            override fun onRequestWithError(error: VolleyError?) {
            }
        })
    }
    interface SendResponseListener{
        fun onRequestStarted()
        fun onRequestCompleted()
        fun onRequestWithError(error: VolleyError?)
    }
    fun sendData(requestData: JSONObject, listener: SendResponseListener){
        val request: JsonObjectRequest = object: JsonObjectRequest(
            Request.Method.POST,
            "https://fcm.googleapis.com/fcm/send",
            requestData,
            object: Response.Listener<JSONObject>{
                override fun onResponse(response: JSONObject?) {
                    listener.onRequestCompleted()
                }
            },
            object: Response.ErrorListener{
                override fun onErrorResponse(error: VolleyError?) {
                    listener.onRequestWithError(error)
                }
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                return params
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers= HashMap<String, String>()
                headers.put("Authorization",
                    "AAAAV2jQBvI:APA91bEJbhaxEQ3ansNnTBo93e739-o3BJH-wR_AUymTqLsFVvaFHVfQYLpQHccm1u823J2rHKxtcImXZixeMUECu5vlQE1ccTYDH2pFUx6VeRtoMFTcMuMuNIRv6A0CPzqJKTa6PzN0")
                return headers
            }

            override fun getBodyContentType(): String {
                return "applicaiton/json"
            }
        }
        request.setShouldCache(false)
        listener.onRequestStarted()
        requestQueue?.add(request)
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