package com.inha.hbc.ui.menu.view

import com.inha.hbc.data.remote.resp.menu.GetMymessageSuccess

interface GetMymessageView {
    fun onGetMymessageSuccess(resp: GetMymessageSuccess)
    fun onGetMymessageFailure()
}