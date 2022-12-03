package com.inha.hbc.util.fragmentmanager

import android.view.View
import androidx.fragment.app.FragmentManager
import com.inha.hbc.data.remote.resp.GetMyInfoBirth
import com.inha.hbc.data.remote.resp.GetMyInfoData
import com.inha.hbc.data.remote.resp.GetMyInfoSuccess
import com.inha.hbc.data.remote.resp.menu.FollowingContent
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.main.ui.MainActivity
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.main.ui.MainMymessageFragment

object MainFragmentManager{
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


    fun refreshPartyRoom(resp: RoomInfoSuccess, followingContent: FollowingContent) {
        val arr = resp.data!![0].cake_type.split("E")
        val cakeType = arr[arr.size - 1].toInt()

        cakeId = cakeSelectionAssist(cakeType)
        roomId = resp.data!![0].room_id
        roomYear = resp.data!![0].birth_date.year

        roominfo = resp
        personInfo = GetMyInfoSuccess(1, GetMyInfoData(authorities = listOf(""),
        birth_date = GetMyInfoBirth(date = followingContent.following.birth_date.date, month = followingContent.following.birth_date.month, year = followingContent.following.birth_date.year, type = followingContent.following.birth_date.type),
            id = followingContent.following.id,
            image_url = followingContent.following.image_url,
            name = followingContent.following.name,
            phone = "",
            username = followingContent.following.username
        ), "", "")

        mainPage.binding.vpMain.adapter!!.notifyDataSetChanged()

        manager.beginTransaction().replace(id, mainPage).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun openLetter(messageId: Int) {
        mainPage.binding.lavMainLoading.visibility = View.VISIBLE
        LetterReadManager.init(manager, mainPage, id)
        LetterReadManager.start(messageId.toString())
    }

    fun transToMessageList() {
        manager.beginTransaction().add(id, MainMymessageFragment()).commit()
        manager.beginTransaction().hide(mainPage).commit()
    }

}