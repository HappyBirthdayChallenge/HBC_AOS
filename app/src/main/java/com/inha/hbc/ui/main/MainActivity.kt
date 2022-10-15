package com.inha.hbc.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.inha.hbc.databinding.ActivityMainBinding
import com.inha.hbc.ui.letter.LetterFragment
import com.inha.hbc.ui.menu.MenuFragment


class MainActivity: AppCompatActivity(), MenuFragment.OnCloseDetection {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initlistener()
        Log.d("life", "oncreate")

    }


    fun initlistener() {
        binding.ablMain.setOnClickListener {
            //gomainpage
        }

        binding.mtbMain.setOnMenuItemClickListener {
            when (it.title) {
                "메뉴"-> {
                    binding.ablMain.visibility = View.GONE
                    binding.fabMain.hide()
                    val menu = MenuFragment(binding.flMain.id)
                    menu.setClose(this)
                    supportFragmentManager.beginTransaction().addToBackStack("menu").add(binding.flMain.id, menu).commit()
                }
                else -> {
                    //내 홈페이지로 이동
                }
            }
            return@setOnMenuItemClickListener true
        }

        binding.fabMain.setOnClickListener {
            binding.ablMain.visibility = View.GONE
            binding.fabMain.hide()
            val letter = LetterFragment()
            supportFragmentManager.beginTransaction().addToBackStack("menu").add(binding.flMain.id, letter).commit()
        }
    }

    override fun onClose() {
        binding.ablMain.visibility = View.VISIBLE
        binding.fabMain.show()
    }

}