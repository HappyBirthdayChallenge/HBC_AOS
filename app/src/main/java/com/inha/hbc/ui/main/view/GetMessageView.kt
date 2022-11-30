package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.message.GetMessageSuccess

interface GetMessageView {
    fun onGetMessageSuccess(resp: GetMessageSuccess)
    fun onGetMessageFailure()
}