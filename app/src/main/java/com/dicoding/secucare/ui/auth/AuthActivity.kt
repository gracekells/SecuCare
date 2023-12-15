package com.dicoding.secucare.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.secucare.R
import com.dicoding.secucare.databinding.ActivityAuthBinding
import com.dicoding.secucare.databinding.ActivityMainBinding
import com.dicoding.secucare.ui.auth.login.LoginFragment

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.auth_container, LoginFragment())
                .commit()
        }
    }
}