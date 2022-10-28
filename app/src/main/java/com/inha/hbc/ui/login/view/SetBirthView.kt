package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.NormSignin

interface SetBirthView {
    fun onSetBirthSuccess(data: NormSignin)
    fun onSetBirthFailure(code: Int)
}