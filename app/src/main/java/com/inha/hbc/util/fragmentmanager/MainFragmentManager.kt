package com.inha.hbc.util.fragmentmanager

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.GetMyInfoBirth
import com.inha.hbc.data.remote.resp.GetMyInfoData
import com.inha.hbc.data.remote.resp.GetMyInfoSuccess
import com.inha.hbc.data.remote.resp.IsMe
import com.inha.hbc.data.remote.resp.menu.Following
import com.inha.hbc.data.remote.resp.menu.FollowingContent
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.data.remote.resp.room.FindMymessage
import com.inha.hbc.data.remote.resp.room.FindMymessageSuccess
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.main.ui.*
import com.inha.hbc.ui.main.view.FindMymessageView
import com.inha.hbc.ui.main.view.Member
import com.inha.hbc.ui.main.view.NotifyContent
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.network.room.RoomRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

object MainFragmentManager: RoomInfoView, FindMymessageView{
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    lateinit var baseActivity: MainActivity
    var id = 0
    var roomId = 0
    var roomYear = 0
    var cakeId = 0
    lateinit var roominfo: RoomInfoSuccess
    lateinit var personInfo: GetMyInfoSuccess

    var notiType = ""

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

    fun transToMenu(memId: Int) {
        MenuFragmentManager.init(manager, id, memId)
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

        RoomRetrofitService().findMymessage(roomId.toString(), this)
    }

    fun refreshPartyRoom(resp: RoomInfoSuccess, followingContent: FollowingContent) {
        val arr = resp.data!![0].cake_type.split("E")
        val cakeType = arr[arr.size - 1].toInt()

        cakeId = cakeSelectionAssist(cakeType)
        roomId = resp.data!![0].room_id
        roomYear = resp.data!![0].birth_date.year

        roominfo = resp
        personInfo = GetMyInfoSuccess(1, GetMyInfoData(authorities = listOf(""),
        birth_date = GetMyInfoBirth(date = followingContent.member.birth_date.date, month = followingContent.member.birth_date.month, year = followingContent.member.birth_date.year, type = followingContent.member.birth_date.type),
            id = followingContent.member.id,
            image_url = followingContent.member.image_url,
            name = followingContent.member.name,
            phone = "",
            username = followingContent.member.username
        ), "", "")

        mainPage.binding.vpMain.adapter!!.notifyDataSetChanged()
        RoomRetrofitService().findMymessage(roomId.toString(), this)
    }
    override fun onFindMymessageSuccess(resp: FindMymessageSuccess) {
        if (resp.data.found) {
            mainPage.binding.ivMainSend.setImageResource(R.drawable.ic_main_message_list)
        }
        else {
            mainPage.binding.ivMainSend.setImageResource(R.drawable.ic_main_send)
        }
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

    fun closeMessageList(fragment: Fragment) {
        manager.beginTransaction().remove(fragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }
    fun transToNotify() {
        manager.beginTransaction().add(id, NotifyFragment()).commit()
        manager.beginTransaction().hide(mainPage).commit()
    }

    fun closeNotify(fragment: Fragment) {
        manager.beginTransaction().remove(fragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun searchToFriend(data: com.inha.hbc.data.remote.resp.main.Result, fragment: Fragment) {
        manager.beginTransaction().remove(fragment).commit()
        MenuFragmentManager.init(manager, id, data.member.id)
        MenuFragmentManager.start(mainPage)
    }

    fun closeSearch(fragment: Fragment) {
        manager.beginTransaction().remove(fragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun notiToElse(type: String, data: NotifyContent, fragment: Fragment) {
        manager.beginTransaction().remove(fragment).commit()
        notiType = type
        when (notiType) {
            "FRIEND" -> {
                MenuFragmentManager.init(manager, id, data.friend_alarm!!.member.id)
                MenuFragmentManager.start(mainPage)
                notiType = ""
            }
            "MESSAGE" -> {
                closeNotify(fragment)
                notiType = ""
            }
            "MESSAGE_LIKE" -> {
                personInfo = GetMyInfoSuccess(1, GetMyInfoData(authorities = listOf(""),
                    birth_date = GetMyInfoBirth(
                        date = data.message_like_alarm!!.member.birth_date.date,
                        month = data.message_like_alarm!!.member.birth_date.month,
                        year = data.message_like_alarm!!.member.birth_date.year,
                        type = data.message_like_alarm!!.member.birth_date.type
                    ),
                    id = data.message_like_alarm!!.member.id,
                    image_url = data.message_like_alarm!!.member.image_url,
                    name = data.message_like_alarm!!.member.name,
                    phone = "",
                    username = data.message_like_alarm!!.member.username
                ), "", "")
                notiMessageId = data.message_like_alarm!!.message_id
                MessageRetrofitService().roomInfo(data.message_like_alarm!!.member.id.toString(), this)

            }
            else -> {//Room
                personInfo = GetMyInfoSuccess(1, GetMyInfoData(authorities = listOf(""),
                birth_date = GetMyInfoBirth(
                    date = data.room_alarm!!.member.birth_date.date,
                    month = data.room_alarm!!.member.birth_date.month,
                    year = data.room_alarm!!.member.birth_date.year,
                    type = data.room_alarm!!.member.birth_date.type
                ),
                id = data.room_alarm!!.member.id,
                image_url = data.room_alarm!!.member.image_url,
                name = data.room_alarm!!.member.name,
                phone = "",
                username = data.room_alarm!!.member.username
                ), "", "")
                MessageRetrofitService().roomInfo(data.room_alarm!!.member.id.toString(), this)
            }
        }
    }
    var notiMessageId = 0
    override fun onRoomInfoSuccess(resp: RoomInfoSuccess) {
        notiType =""
        if (notiType == "MESSAGE_LIKE") {
            refreshPartyRoom(resp)
            openLetter(notiMessageId)
        }
        else {
            refreshPartyRoom(resp)
        }
    }

    override fun onRoomInfoFailure() {
        TODO("Not yet implemented")
    }

    fun transToSearch() {
        manager.beginTransaction().add(id, SearchFragment()).commit()
        manager.beginTransaction().hide(mainPage).commit()
    }


    override fun onFindMymessageFailure() {
        TODO("Not yet implemented")
    }
}