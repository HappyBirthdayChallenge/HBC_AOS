package com.inha.hbc.ui.menu.view

import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess

interface GetProfileView {
    fun onGetProfileSuccess(resp: GetProfileSuccess)
    fun onGetProfileFailure()
}