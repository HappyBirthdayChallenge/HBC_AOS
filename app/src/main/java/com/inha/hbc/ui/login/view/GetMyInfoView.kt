package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.GetMyInfoSuccess

interface GetMyInfoView {
    fun onGetMyInfoSuccess(resp: GetMyInfoSuccess)
    fun onGetMyInfoFailure()
}