package com.inha.hbc.util.fragmentmanager

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.local.LetterData
import com.inha.hbc.ui.adapter.LetterMediaListRVAdapter
import com.inha.hbc.ui.letter.ui.*
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

    var pathArr = ArrayList<String>()
    var uriArr = ArrayList<Uri>()
    var typeArr = ArrayList<Int>() // 0 사진 1 동영상 2 음성

    lateinit var mediaAdapter: LetterMediaListRVAdapter

    lateinit var letterFragment: LetterFragment

    fun init(manager: FragmentManager, id: Int, base: MainActivity) {
        this.manager = manager
        this.id = id
        mainPage = MainFragment()
        baseActivity = base
        letterData.animeName = "json_deco_anime_1.json"
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
        pathArr.clear()
        uriArr.clear()
        typeArr.clear()
        manager.beginTransaction().remove(letterBaseFragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun objectOpen(type: String) {
        objectPageType = when(type) {
            "img_deco_gift_" -> 0
            "img_deco_drink_" -> 1
            "img_deco_toy_" -> 2
            "img_pic_" -> 3
            else -> 4//음식
        }

        manager.beginTransaction().add(id, ObjectSelectionFragment()).commit()
    }

    fun objectClose(page: Fragment, selected: Boolean) {
        manager.beginTransaction().remove(page).commit()
        if (selected) {
            letterBaseFragment.getObject()
        }
    }

    fun animeSelected(title: String) {
        letterData.animeName = title
        letterBaseFragment.getAnime()
    }

    fun openRecording() {
        manager.beginTransaction().add(id, LetterRecordFragment()).commit()
    }

    fun recordClose(uri: Uri, page: Fragment) {
        uriArr.add(uri)
        typeArr.add(2)
        pathArr.add("")
        mediaAdapter.notifyItemInserted(uriArr.size)
        manager.beginTransaction().remove(page).commit()
    }

    fun recordClose(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
    }

    fun updateData(uri: Uri, type: Int, path: String) {
        uriArr.add(uri)
        typeArr.add(type)
        pathArr.add(path)
        mediaAdapter.notifyItemInserted(uriArr.size)
    }

    fun openShow(pos: Int) {
        manager.beginTransaction().add(id, LetterShowFragment(pos - 1)).commit()
    }

    fun closeShow(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
    }

}