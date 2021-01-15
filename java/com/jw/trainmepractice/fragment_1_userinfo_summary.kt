package com.jw.trainmepractice

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

// fragment_1(메인화면) 에서 유저의 개인 정보를 담는 부분을 따로 만들려고 했던 부분.
// 지금 코드에서는 안쓰이지만 추후에 화면을 바꿀 때
// if (uid == null) ----> x만 있는 빈 화면을 보여주고
// else ----> 지금의 화면을 보여준다
// 이런 식으로 만들면 될거같지만 잘 될까...?


class fragment_1_userinfo_summary(): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_1_userinfo_summary, container, false)

        view.findViewById<FloatingActionButton>(R.id.Button_to_changeInfo).setOnClickListener {
            startActivity(Intent(getActivity(), UserMainProfile::class.java))
        }

        return view
    }
}