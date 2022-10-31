package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.PhoneFailure
import com.inha.hbc.data.remote.resp.PhoneSuccess

interface CheckPhoneView {
    fun onResponseSuccess()
    fun onResponseFailure(data: PhoneSuccess)
    fun onResponseFailure(data: PhoneFailure)
    fun onResponseFailure()
}