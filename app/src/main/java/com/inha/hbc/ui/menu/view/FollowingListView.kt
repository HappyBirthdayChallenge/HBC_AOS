package com.inha.hbc.ui.menu.view

import com.inha.hbc.data.remote.resp.menu.FollowingListSuccess

interface FollowingListView {
    fun onFollowingListSuccess(resp: FollowingListSuccess)
    fun onFollowingListFailure()
}