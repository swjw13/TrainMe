package com.jw.trainmepractice

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

// 메인 탭을 구성하는 부분
// 우리의 화면을 정확히 설명하면 MainActivity 안에 fragment_1 ~ 4를 넣는 방법임
// 크게 볼 건 없을 거 같고 page를 넘길 때 신기한 효과를 주는 방법(뭐 페이지 넘기듯이 넘어가거나, 화려하게 넘어가는거) 도 있고
// 탭에 아이콘을 넣는 방법? 도 있던데 그건 추후에 찾아 볼 예정

class MainActivity : AppCompatActivity() {
    lateinit var backPressedForFinish: BackPressedForFinish

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager22)
        val tab = findViewById<TabLayout>(R.id.main_tab)
        val tabTextList = arrayListOf("메인","추천","채팅","메뉴")

        val adapter = ViewPagerAdapter2(this)
        viewPager2.adapter = adapter
        TabLayoutMediator(tab, viewPager2){ tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }
    override fun onBackPressed() {
        // BackPressedForFinish 클래시의 onBackPressed() 함수를 호출한다.
        backPressedForFinish.onBackPressed()
    }
}

class ViewPagerAdapter2(
    val fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragment_1(fragmentActivity)
            1 -> fragment_2(fragmentActivity)
            2 -> fragment_3(fragmentActivity)
            3 -> fragment_4(fragmentActivity)
            else->fragment_1(fragmentActivity)
        }
    }
}
