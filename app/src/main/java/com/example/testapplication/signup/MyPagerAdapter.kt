package com.example.testapplication.signup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(
    private val list: MutableList<Fragment>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Fragment = list[position]
}