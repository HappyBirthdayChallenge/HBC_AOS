package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.FragmentManager
import com.inha.hbc.ui.main.MainFragment

object MainFragmentManager {
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    var id = 0

    fun init(manager: FragmentManager, id: Int) {
        this.manager = manager
        this.id = id
        mainPage = MainFragment()
    }

    fun start() {
        manager.beginTransaction().add(id, mainPage).commit()
    }
}