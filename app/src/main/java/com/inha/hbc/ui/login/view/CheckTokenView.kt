package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.CheckTokenSuccess

interface CheckTokenView {
    fun onCheckTokenSuccess()
    fun onCheckTokenFailure()
}