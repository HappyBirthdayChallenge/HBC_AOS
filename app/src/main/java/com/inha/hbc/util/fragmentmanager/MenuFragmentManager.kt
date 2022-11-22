package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.menu.ui.MenuFragment

object MenuFragmentManager {

    lateinit var manager: FragmentManager
    var id = 0
    lateinit var mainPage: MainFragment
    lateinit var menuPage: MenuFragment

    fun init(manager: FragmentManager, containerId: Int) {
        this.manager = manager
        id = containerId
    }

    fun start(main: MainFragment) {
        mainPage = main
        menuPage = MenuFragment()
        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().add(id, menuPage).commit()
    }


    fun close() {
        manager.beginTransaction().remove(menuPage).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun openMenu(menu: String) {

    }

}