package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.main.GlobalSearchSuccess

interface GlobalSearchView {
    fun onGlobalSearchSuccess(resp: GlobalSearchSuccess)
    fun onGlobalSearchFailure()
}