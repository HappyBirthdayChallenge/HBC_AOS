package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.ui.letter.LetterFragment
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.ui.main.MainFragment
import com.inha.hbc.ui.menu.MenuFragment

object MainFragmentManager {
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    lateinit var baseActivity: MainActivity
    var id = 0

    fun init(manager: FragmentManager, id: Int, base: MainActivity) {
        this.manager = manager
        this.id = id
        mainPage = MainFragment()
        baseActivity = base
    }

    fun start() {
        manager.beginTransaction().add(id, mainPage).commit()
    }

    fun transToMenu() {
        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().add(id, MenuFragment()).commit()
    }

    fun transToLetter() {
        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().add(id, LetterFragment()).commit()
    }

    fun menuClose(menu: Fragment) {
        manager.beginTransaction().remove(menu).commit()
        manager.beginTransaction().show(mainPage).commit()
    }
}