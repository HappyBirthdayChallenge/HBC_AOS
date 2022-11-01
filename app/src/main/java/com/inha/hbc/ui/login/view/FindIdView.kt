package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.FindIdSuccess

interface FindIdView {
    fun onFindIdSuccess(respData: FindIdSuccess)
    fun onFindIdFailure()
}