package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.data.remote.resp.menu.*
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.menu.ui.FriendListFragment
import com.inha.hbc.ui.menu.ui.MypageFragment
import com.inha.hbc.ui.menu.view.GetProfileView
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

object MenuFragmentManager: GetProfileView{

    lateinit var manager: FragmentManager
    var id = 0
    var memberId = 0
    lateinit var mainPage: MainFragment
    lateinit var menuPage: MypageFragment

    fun init(manager: FragmentManager, containerId: Int, memId: Int) {
        this.manager = manager
        id = containerId
        memberId = memId
    }

    fun start(main: MainFragment) {
        mainPage = main
        MenuRetrofitService().getProfile(memberId.toString(), this)
    }

    override fun onGetProfileSuccess(resp: GetProfileSuccess) {
        menuPage = MypageFragment(resp)
        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().add(id, menuPage).commit()
    }


    fun close() {
        manager.beginTransaction().remove(menuPage).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun openMenu(menu: String) {
        when (menu) {
            "친구목록" -> {
                manager.beginTransaction().add(id, FriendListFragment(1)).commit()
            }
            else -> {

            }
        }
    }

    fun openFriendList(pos: Int) {
        if (pos == 0) {
            manager.beginTransaction().add(id, FriendListFragment(0)).commit()
        }
        else {
            manager.beginTransaction().add(id, FriendListFragment(1)).commit()
        }
    }

    fun closeFriendList(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
        manager.beginTransaction().show(menuPage).commit()
    }

    fun goPartyRoom(resp: RoomInfoSuccess, info: FollowingContent) {
        MainFragmentManager.refreshPartyRoom(resp, info)
    }
    fun goPartyRoom(resp: RoomInfoSuccess, info: FollowerContent) {
        val infoo = FollowingContent(
            info.follow, Following(
            birth_date = info.member.birth_date,
            username = info.member.username,
            name = info.member.name,
            image_url = info.member.image_url,
            id = info.member.id
        ))
        MainFragmentManager.refreshPartyRoom(resp, infoo)
    }

    fun goPartyRoom(data: GetProfileSuccess) {
        val resp = RoomInfoSuccess(code = "", data = data.data.rooms, message = "", status = 0)
        val info = FollowingContent(
            false, Following(
            name = data.data.member.name,
            id = data.data.member.id,
            image_url = data.data.member.image_url,
            username = data.data.member.username,
            birth_date = data.data.member.birth_date
        ))
        MainFragmentManager.refreshPartyRoom(resp, info)
    }

    fun goPartyRoom(resp: RoomInfoSuccess, data: Content) {
        val info = FollowingContent(
            false, Following(
            name = data.room_owner.name,
            id = data.room_owner.id,
            image_url = data.room_owner.image_url,
            username = data.room_owner.username,
            birth_date = data.room_owner.birth_date
        ))
        MainFragmentManager.refreshPartyRoom(resp, info)
    }


    override fun onGetProfileFailure() {
        TODO("Not yet implemented")
    }

}