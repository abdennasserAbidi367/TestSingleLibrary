package com.example.testapplication.signup

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipeableViewPager : ViewPager {

    constructor(context: Context): super(context) {
        Log.e("NoSwipeableViewPager", "constractor with one key")
    }

    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet) {
        Log.e("NoSwipeableViewPager", "constractor with two key")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = false
}