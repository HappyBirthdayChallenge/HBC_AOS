package com.inha.hbc.ui.login.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.inha.hbc.databinding.ActivityLoginBinding
import com.inha.hbc.util.NormLoginFragmentManager
import com.inha.hbc.util.SignupFragmentManager


class LoginActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        SignupFragmentManager.setManager(supportFragmentManager, binding.fcLogin.id, this)
        NormLoginFragmentManager.setManager(supportFragmentManager, binding.fcLogin.id, this)

        supportFragmentManager.beginTransaction().add(binding.fcLogin.id, LoginFragment()).commit()
    }

    fun hideKeyboard() {
        val keyboard = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}