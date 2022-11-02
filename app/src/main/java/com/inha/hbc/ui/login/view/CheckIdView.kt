package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.CheckIdFailure
import com.inha.hbc.data.remote.resp.CheckIdSuccess

interface CheckIdView {
    fun onResponseSuccess(resp: CheckIdSuccess)
    fun onResponseFailure(resp: CheckIdSuccess)
    fun onResponseFailure(resp: CheckIdFailure)
    fun onResponseFailure(resp: String)
}