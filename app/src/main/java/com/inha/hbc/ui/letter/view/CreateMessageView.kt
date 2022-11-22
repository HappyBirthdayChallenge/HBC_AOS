package com.inha.hbc.ui.letter.view

import com.inha.hbc.data.remote.resp.message.CreateMessageSuccess

interface CreateMessageView {
    fun onCreateMessageSuccess(resp: CreateMessageSuccess)
    fun onCreateMessageFailure()
}