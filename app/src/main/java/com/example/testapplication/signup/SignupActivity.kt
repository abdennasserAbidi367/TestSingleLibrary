package com.example.testapplication.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.testapplication.R

class SignupActivity : AppCompatActivity(), ActivityInteractor {

    lateinit var viewPager: NoSwipeableViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        viewPager = findViewById(R.id.vp)
        val myPagerAdapter = MyPagerAdapter(
            mutableListOf(
                FirstFragment.newInstance(this),
                SecondFragment.newInstance(),
                ThirdFragment.newInstance()
            ),
            supportFragmentManager
        )
        viewPager.adapter = myPagerAdapter
    }

    override fun onClick() {
        viewPager.currentItem = 1
    }
}