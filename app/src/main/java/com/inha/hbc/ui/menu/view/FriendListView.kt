package com.inha.hbc.ui.menu.view

import com.inha.hbc.data.remote.resp.menu.FriendlistSuccess

interface FriendListView {
    fun onFriendListSuccess(resp: FriendlistSuccess)
    fun onFriendListFailure()
}