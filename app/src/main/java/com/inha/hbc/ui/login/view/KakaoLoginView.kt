package com.inha.hbc.ui.login.view

interface KakaoLoginView {
    fun onKakaoLoginSuccess()
    fun onKakaoLoginFailure(code: Int)
}