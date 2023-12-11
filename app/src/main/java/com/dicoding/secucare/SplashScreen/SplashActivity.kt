package com.dicoding.secucare.SplashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.dicoding.secucare.R
import com.dicoding.secucare.ui.adapter.SplashPagerAdapter

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val viewPager: ViewPager = findViewById(R.id.viewPagerSplash)
        val adapter = SplashPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }
}