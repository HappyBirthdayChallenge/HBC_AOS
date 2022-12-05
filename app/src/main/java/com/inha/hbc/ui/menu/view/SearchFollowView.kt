package com.inha.hbc.ui.menu.view

import com.inha.hbc.data.remote.resp.main.GlobalSearchSuccess

interface SearchFollowView {
    fun onSearchFollowSuccess(resp: GlobalSearchSuccess, type: String)
    fun onSearchFollowFailure()
}