package com.inha.hbc.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.inha.hbc.databinding.ActivityMainBinding
import com.inha.hbc.ui.letter.LetterFragment
import com.inha.hbc.ui.menu.MenuFragment
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        MainFragmentManager.init(supportFragmentManager, binding.fcMain.id)
        MainFragmentManager.start()

    }
}