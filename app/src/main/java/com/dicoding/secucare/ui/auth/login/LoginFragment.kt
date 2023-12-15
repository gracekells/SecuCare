package com.dicoding.secucare.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.dicoding.secucare.R
import com.dicoding.secucare.ui.auth.AuthViewModel
import com.dicoding.secucare.ui.auth.AuthViewModelFactory
import com.dicoding.secucare.databinding.FragmentLoginBinding
import com.dicoding.secucare.service.ApiConfig
import com.dicoding.secucare.ui.auth.register.RegisterFragment
import com.dicoding.secucare.ui.main.MainActivity

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            ApiConfig.getApiService(),
            getInstance(requireContext().dataStore)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupPlayAnimation()

        observeToken()
        observeLoading()
        observeMessage()

        setListeners()

        return binding.root
    }

    private fun observeToken() {
        authViewModel.getToken().observe(viewLifecycleOwner) { token ->
            if (token != preferenceDefaultValue) {
                val iMain = Intent(requireContext(), MainActivity::class.java)
                iMain.putExtra(USER_TOKEN, token)
                requireActivity().finish()
                startActivity(iMain)
            }
        }
    }

    private fun observeLoading() {
        authViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun observeMessage(){
        authViewModel.toastMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (!Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.text.toString())
                        .matches() && !binding.edEmail.text.isNullOrEmpty()
                ) {
                    showToast("Invalid Email!")
                } else if (!binding.edPassword.error.isNullOrEmpty() || binding.edPassword.text.isNullOrEmpty()) {
                    showToast("Invalid Password!")
                } else {
                    authViewModel.loginUser(edEmail.text.toString(), edPassword.text.toString())
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is Result.Loading -> {
                                    authViewModel.isLoading.postValue(true)
                                }

                                is Result.Success -> {
                                authViewModel.isLoading.postValue(false)
                                authViewModel.saveToken(result.data.loginResult.token)
                                observeToken()
                            }

                            is Result.Error -> {
                                authViewModel.isLoading.postValue(false)
                                authViewModel.toastMessage.postValue(Event(result.error))
                            }
                        }
                    }
            }
        }

            btnRegister.setOnClickListener {
                changeRegister()
            }
        }
    }

    private fun changeRegister() {
        parentFragmentManager.beginTransaction().apply {
            replace(
                R.id.auth_container,
                RegisterFragment(),
                RegisterFragment::class.java.simpleName
            )
            commit()
        }
    }

    @SuppressLint("Recycle")
    private fun setupPlayAnimation(){
        val tvTitle = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(150)
        val edEmail = ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f).setDuration(150)
        val edPassword = ObjectAnimator.ofFloat(binding.edPassword, View.ALPHA, 1f).setDuration(150)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(150)
        val layoutText = ObjectAnimator.ofFloat(binding.layoutText, View.ALPHA, 1f).setDuration(150)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(
            tvTitle, edEmail, edPassword, btnLogin, layoutText
        )
        animatorSet.startDelay = 150
        animatorSet.start()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressbar.isVisible = isLoading
            btnLogin.isVisible = !isLoading
            edEmail.isEnabled = !isLoading
            edPassword.isEnabled = !isLoading
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}