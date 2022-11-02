package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.NormFailure
import com.inha.hbc.data.remote.resp.NormSuccess

interface NormLoginView {
    fun onNormLoginSuccess(data: NormSuccess)
    fun onNormLoginFailure(data: NormSuccess)
    fun onNormLoginFailure(data: NormFailure)
    fun onNormLoginFailure(message: String)
}