package com.dicoding.secucare.ui.auth.register

import android.os.Bundle
import android.provider.Settings
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.secucare.R
import com.dicoding.secucare.databinding.FragmentRegisterBinding
import com.dicoding.secucare.service.ApiConfig
import com.dicoding.secucare.ui.auth.AuthViewModel
import com.dicoding.secucare.ui.auth.AuthViewModelFactory

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            ApiConfig.getApiService(),
            SettingsPreferences.getInstance(requireContext().dataStore)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setupPlayAnimation()

        observeLoading()
        observeMessage()

        setListeners()

        return binding.root
    }

    private fun observeLoading() {
        authViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun observeMessage() {
        authViewModel.toastMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnRegister.setOnClickListener {
                if (binding.edUsername.text.isNullOrEmpty()) {
                    showToast("Invalid Username!")
                } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.text.toString())
                        .matches() && !binding.edEmail.text.isNullOrEmpty()
                ) {
                    showToast("Invalid Email Address!")
                } else if (!binding.edPassword.error.isNullOrEmpty() || binding.edPassword.text.isNullOrEmpty()) {
                    showToast("Invalid Password!")
                } else {
                    authViewModel.registerUser(
                        edUsername.text.toString()
                        edEmail.text.toString()
                        edPassword.text.toString()
                    )
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    authViewModel.isLoading.postValue(true)
                                }

                                is Result.Success -> {
                                    authViewModel.isLoading.postValue(false)
                                    showToast(result.data.message.toString())

                                    changeLogin()
                                }

                                is Result.Error -> {
                                    authViewModel.isLoading.postValue(false)
                                    authViewModel.toastMessage.postValue(Event(result.error))
                                }
                            }
                        }
                }
            }
        }
    }
}