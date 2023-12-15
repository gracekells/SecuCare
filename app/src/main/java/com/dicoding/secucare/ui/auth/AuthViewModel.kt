package com.dicoding.secucare.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.secucare.ui.auth.repo.AuthRepository
import com.dicoding.secucare.ui.utils.Event
import com.dicoding.secucare.ui.utils.SettingsPreferences

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val toastMessage = MutableLiveData<Event<String>>()

    fun loginUser(email: String, password: String) =
        authRepository.loginUser(email, password)

    fun registerUser(name: String, email: String, password: String) =
        authRepository.registerUser(name, email, password)

    fun saveToken(
        token: String,
    ) {
        viewModelScope.launch {
            settingsPreferences.saveToken(token)
        }
    }

    fun getToken() = settingsPreferences.getToken().asLiveData()
}