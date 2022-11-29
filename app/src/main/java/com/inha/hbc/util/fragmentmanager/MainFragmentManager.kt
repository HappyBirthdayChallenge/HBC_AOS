package com.inha.hbc.util.fragmentmanager

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.local.LetterData
import com.inha.hbc.data.remote.resp.GetMyInfoSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.ui.adapter.LetterMediaListRVAdapter
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.letter.ui.*
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.main.ui.MainActivity
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.menu.ui.MenuFragment
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

object MainFragmentManager {
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    lateinit var baseActivity: MainActivity
    var id = 0
    var roomId = 0
    var roomYear = 0
    var cakeId = 0
    lateinit var roominfo: RoomInfoSuccess
    lateinit var personInfo: GetMyInfoSuccess


    fun init(manager: FragmentManager, id: Int, data: RoomInfoSuccess, info: GetMyInfoSuccess, base: MainActivity) {
        val arr = data.data!![0].cake_type.split("E")
        val cakeType = arr[arr.size - 1].toString().toInt()

        this.manager = manager
        this.id = id
        mainPage = MainFragment()
        baseActivity = base
        cakeId =  cakeSelectionAssist(cakeType)
        roomYear = data.data!![0].birth_date.year
        this.roomId = data.data!![0].room_id
        roominfo = data
        personInfo = info
    }

    fun start() {
        manager.beginTransaction().add(id, mainPage).commit()
    }

    fun transToMenu() {
        MenuFragmentManager.init(manager, id)
        MenuFragmentManager.start(mainPage)
    }

    fun transToLetter() {
        LetterFragmentManager.init(manager, mainPage, id)
        LetterFragmentManager.start(roomId.toString())
    }


    fun refreshPartyRoom(resp: RoomInfoSuccess) {
        val arr = resp.data!![0].cake_type.split("E")
        val cakeType = arr[arr.size - 1].toInt()

        cakeId = cakeSelectionAssist(cakeType)
        roomId = resp.data!![0].room_id
        roomYear = resp.data!![0].birth_date.year

        roominfo = resp

        mainPage.binding.vpMain.adapter!!.notifyDataSetChanged()

        manager.beginTransaction().replace(id, mainPage).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

}