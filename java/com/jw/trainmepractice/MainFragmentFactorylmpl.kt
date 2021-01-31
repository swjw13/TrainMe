package com.jw.trainmepractice

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import com.jw.trainmepractice.trainerMainFragment.fragment1_trainer
import com.jw.trainmepractice.trainerMainFragment.fragment2_trainer
import com.jw.trainmepractice.trainerMainFragment.fragment3_trainer
import com.jw.trainmepractice.userMainFragment.fragment_1
import com.jw.trainmepractice.userMainFragment.fragment_2
import com.jw.trainmepractice.userMainFragment.fragment_3
import com.jw.trainmepractice.userMainFragment.fragment_4

class MainFragmentFactoryImpl(val frag: FragmentActivity): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            fragment_1::class.java.name -> fragment_1(frag)
            fragment_2::class.java.name -> fragment_2(frag)
            fragment_3::class.java.name -> fragment_3(frag)
            fragment_4::class.java.name -> fragment_4(frag)
            fragment1_trainer::class.java.name -> fragment1_trainer(frag)
            fragment2_trainer::class.java.name -> fragment2_trainer(frag)
            fragment3_trainer::class.java.name -> fragment3_trainer(frag)
            else -> super.instantiate(classLoader, className)
        }
    }
}