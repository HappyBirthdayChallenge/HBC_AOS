package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.NormSigninBody

interface SetBirthView {
    fun onSetBirthSuccess(data: NormSigninBody)
    fun onSetBirthFailure(code: Int)
}