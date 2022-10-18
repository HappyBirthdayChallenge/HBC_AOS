package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.ErrorResp
import com.inha.hbc.data.remote.resp.NormSigninBody

interface NormLoginView {
    fun onNormLoginSuccess(data: NormSigninBody)
    fun onNormLoginFailure(data: ErrorResp)
    fun onNormLoginFailure(data: Int)
}