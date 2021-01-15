package com.jw.trainmepractice

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 주로 사용하는 함수나 어댑터를 정리한 부분
// 토스트 만들기, 스피너 어댑터 정리 등등

fun makeToast(context: Context, text: String){
    Toast.makeText(context, text , Toast.LENGTH_SHORT).show()
}

// spinner String Adapter
fun getAdapter(context: Context, list: List<String>): ArrayAdapter<String> {
    return ArrayAdapter<String>(context, R.layout.simple_spinner_dropdown_item, list)
}