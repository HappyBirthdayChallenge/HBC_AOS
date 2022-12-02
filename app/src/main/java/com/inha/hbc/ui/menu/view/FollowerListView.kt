package com.inha.hbc.ui.menu.view

import com.inha.hbc.data.remote.resp.menu.FollowerListSuccess

interface FollowerListView {
    fun onFollowerListSuccess(resp: FollowerListSuccess)
    fun onFollowerListFailure()
}