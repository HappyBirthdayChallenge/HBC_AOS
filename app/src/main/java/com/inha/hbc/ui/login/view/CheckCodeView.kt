package com.inha.hbc.ui.login.view

import com.inha.hbc.data.remote.resp.CodeFailure
import com.inha.hbc.data.remote.resp.CodeSuccess

interface CheckCodeView {
    fun onCheckCodeResponseSuccess(respData: CodeSuccess)
    fun onCheckCodeResponseFailure(respData: CodeSuccess)
    fun onCheckCodeResponseFailure(respData: CodeFailure)
    fun onCheckCodeResponseFailure()
}