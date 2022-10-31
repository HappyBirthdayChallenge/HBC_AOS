package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.Data

interface KakaoLoginView {
    fun onKakaoLoginSuccess(data: Data)
    fun onKakaoLoginFailure(code: Int)
}