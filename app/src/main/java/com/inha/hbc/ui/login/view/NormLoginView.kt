package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.NormSignin

interface NormLoginView {
    fun onNormLoginSuccess(data: NormSignin)
    fun onNormLoginFailure()
}