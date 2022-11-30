package com.inha.hbc.util.fragmentmanager

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.local.LetterData
import com.inha.hbc.data.remote.resp.GetMyInfoBirth
import com.inha.hbc.data.remote.resp.GetMyInfoData
import com.inha.hbc.data.remote.resp.GetMyInfoSuccess
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.data.remote.resp.message.GetMessageSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.ui.adapter.LetterMediaListRVAdapter
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.letter.ui.*
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.main.ui.MainActivity
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.main.view.GetMessageView
import com.inha.hbc.ui.menu.ui.MenuFragment
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

object MainFragmentManager: GetMessageView{
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


    fun refreshPartyRoom(resp: RoomInfoSuccess, content: Content) {
        val arr = resp.data!![0].cake_type.split("E")
        val cakeType = arr[arr.size - 1].toInt()

        cakeId = cakeSelectionAssist(cakeType)
        roomId = resp.data!![0].room_id
        roomYear = resp.data!![0].birth_date.year

        roominfo = resp
        personInfo = GetMyInfoSuccess(1, GetMyInfoData(authorities = listOf(""),
        birth_date = GetMyInfoBirth(date = content.member.birth_date.date, month = content.member.birth_date.month, year = content.member.birth_date.year, type = content.member.birth_date.type),
            id = content.member.id,
            image_url = content.member.image_url,
            name = content.member.name,
            phone = "",
            username = content.member.username
        ), "", "")

        mainPage.binding.vpMain.adapter!!.notifyDataSetChanged()

        manager.beginTransaction().replace(id, mainPage).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun openLetter(messageId: Int) {
        MessageRetrofitService().getMessage(messageId.toString(), this)
        mainPage.binding.lavMainLoading.visibility = View.VISIBLE


    }

    override fun onGetMessageSuccess(resp: GetMessageSuccess) {
        manager.beginTransaction().add(id, LetterReadFragment(resp, true)).commit()
        manager.beginTransaction().hide(mainPage).commit()
        mainPage.binding.lavMainLoading.visibility = View.GONE
    }

    override fun onGetMessageFailure() {
    }

}