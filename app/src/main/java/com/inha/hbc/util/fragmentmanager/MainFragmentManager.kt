package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.local.LetterData
import com.inha.hbc.ui.assist.selectionAssist
import com.inha.hbc.ui.letter.LetterBaseFragment
import com.inha.hbc.ui.letter.LetterFragment
import com.inha.hbc.ui.letter.ObjectSelectionFragment
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.ui.main.MainFragment
import com.inha.hbc.ui.menu.ui.MenuFragment

object MainFragmentManager {
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    lateinit var baseActivity: MainActivity
    lateinit var letterBaseFragment: LetterBaseFragment
    var id = 0

    var objectPageType = 0
    var letterData = LetterData("", "", "")
    var objectId = R.drawable.img_deco_drink_1
    var viewWidth = 0

    lateinit var letterFragment: LetterFragment

    fun init(manager: FragmentManager, id: Int, base: MainActivity) {
        this.manager = manager
        this.id = id
        mainPage = MainFragment()
        baseActivity = base
        letterData.animeId = "json_deco_anime_1.json"
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
        letterBaseFragment = LetterBaseFragment()
        manager.beginTransaction().add(id, letterBaseFragment).commit()
    }

    fun menuClose(menu: Fragment) {
        manager.beginTransaction().remove(menu).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun letterClose() {
        manager.beginTransaction().remove(letterBaseFragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun objectOpen(type: String) {
        manager.beginTransaction().add(id, ObjectSelectionFragment()).commit()
        objectPageType = when(type) {
            "img_deco_gift_" -> 0
            "img_deco_drink_" -> 1
            "img_deco_toy_" -> 2
            "img_pic_" -> 3
            else -> 4//음식
        }
    }

    fun objectClose(page: Fragment, selected: Boolean) {
        manager.beginTransaction().remove(page).commit()
        if (selected) {
            letterBaseFragment.getObject()
        }
    }

    fun animeSelected(title: String) {
        letterData.animeId = title
        letterBaseFragment.getAnime()
    }

}