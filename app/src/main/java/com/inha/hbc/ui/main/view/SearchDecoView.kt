package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.message.SearchDecoSuccess

interface SearchDecoView {
    fun onSearchDecoSuccess(resp: SearchDecoSuccess)
    fun onSearchDecoFailure()
}