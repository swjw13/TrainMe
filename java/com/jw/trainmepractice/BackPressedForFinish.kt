package com.jw.trainmepractice

import android.app.Activity
import android.widget.Toast

// 뒤로가기 두 번 눌렀을 때 앱을 종료하기 위한 class
// 메인 화면에서 사용되고
// 굳이 이해할 필요는 ㄴㄴ

class BackPressedForFinish(
    private val activity: Activity
) {
    private var backKeyPressedTime: Long = 0
    private val TIME_INTERVAL: Long = 2000
    private var toast : Toast? = null

    fun onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + TIME_INTERVAL) {
            backKeyPressedTime = System.currentTimeMillis()
            showMessage()
        } else {
            toast!!.cancel()
            activity.finish()
        }
    }

    fun showMessage() {
        val toast = Toast.makeText(activity, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        toast.show()
    }

}