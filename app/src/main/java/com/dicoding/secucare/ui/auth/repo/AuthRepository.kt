package com.dicoding.secucare.ui.auth.repo

import com.dicoding.secucare.service.ApiService
import java.security.interfaces.RSAKey

class AuthRepository(
    private val apiService: ApiService
) {
    fun loginUser(email: String, password: String) = liveData {
        emit(Result.Loading)
        try {
            val loginUser = apiService.login(email, password)
            emit(Result.Success(loginUser))
        }catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }

    fun registerUser(
        name: String,
        email: String,
        password: String
    ) = liveData {
        emit(Result.Loading)
        try {
            val registerUser = apiService.register(name, email, password)
            emit(Result.Success(registerUser))
        }catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }
}