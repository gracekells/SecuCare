package com.dicoding.secucare.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.secucare.fragment.SplashFragment

class SplashPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return SplashFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return 4
    }
}