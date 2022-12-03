package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.room.GetReceiveMessageSuccess

interface GetReceiveMessageView {
    fun onGetReceiveMessageSuccess(resp: GetReceiveMessageSuccess)
    fun onGetReceiveMessageFailure()
}