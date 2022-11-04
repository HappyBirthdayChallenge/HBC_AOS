package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.GetTokenSuccess

interface GetTokenView {
    fun onGetTokenSuccess(resp: GetTokenSuccess)
    fun onGetTokenFailure()
}