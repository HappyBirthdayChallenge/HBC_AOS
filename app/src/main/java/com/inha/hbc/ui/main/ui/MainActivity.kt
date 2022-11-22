package com.inha.hbc.ui.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inha.hbc.databinding.ActivityMainBinding
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ext = intent.extras
        val cakeType = ext!!.getInt("caketype")
        val year = ext!!.getInt("year")
        val roomId = ext!!.getInt("roomId")

        MainFragmentManager.init(supportFragmentManager, binding.fcMain.id, cakeType, year, roomId, this)
        MainFragmentManager.start()

    }
}