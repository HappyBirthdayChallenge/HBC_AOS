package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.data.remote.resp.menu.FollowerContent
import com.inha.hbc.data.remote.resp.menu.Following
import com.inha.hbc.data.remote.resp.menu.FollowingContent
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.menu.ui.FriendListFragment
import com.inha.hbc.ui.menu.ui.MypageFragment

object MenuFragmentManager {

    lateinit var manager: FragmentManager
    var id = 0
    lateinit var mainPage: MainFragment
    lateinit var menuPage: MypageFragment

    fun init(manager: FragmentManager, containerId: Int) {
        this.manager = manager
        id = containerId
    }

    fun start(main: MainFragment) {
        mainPage = main
        menuPage = MypageFragment()
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

    fun setFriendFollower(num: Int) {

    }

    fun goPartyRoom(resp: RoomInfoSuccess, info: FollowingContent) {
        MainFragmentManager.refreshPartyRoom(resp, info)
    }
    fun goPartyRoom(resp: RoomInfoSuccess, info: FollowerContent) {
        val infoo = FollowingContent(Following(
            birth_date = info.follower.birth_date,
            username = info.follower.username,
            name = info.follower.name,
            image_url = info.follower.image_url,
            id = info.follower.id
        ))
        MainFragmentManager.refreshPartyRoom(resp, infoo)
    }

}