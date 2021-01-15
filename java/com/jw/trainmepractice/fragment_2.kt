package com.jw.trainmepractice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// 탭의 두번째 화면(조건별로 트레이너를 나열하는 부분) 을 구성하는 fragment
// 지금은 그냥 recycleview하나만 넣어놓고 아무것도 안함
// 그냥 리스너만 달아놓고 있으려고 했는데 오류가 나네
// 젠장

class fragment_2(val fragment: FragmentActivity) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_2_showall, container, false)

        val adapter = fragment2_adapter(LayoutInflater.from(fragment),fragment)
        val listView = view.findViewById<RecyclerView>(R.id.frag2_list)
        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(fragment)

//        val db_realtime = Firebase.database.reference
//        db_realtime.child("trainer").addChildEventListener(object: ChildEventListener{
//            override fun onCancelled(error: DatabaseError) {}
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                adapter.update(snapshot.value as Trainer)
//                adapter.notifyDataSetChanged()
//            }
//            override fun onChildRemoved(snapshot: DataSnapshot) {}
//        })

        return view
    }
}
class fragment2_adapter(
    val inflater: LayoutInflater,
    val fragment: FragmentActivity
) : RecyclerView.Adapter<fragment2_adapter.ViewHolder>() {

    var list = arrayListOf<Trainer>()

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
    fun update(item: Trainer){
        list.add(item)
    }
}