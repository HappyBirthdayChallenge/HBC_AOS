package com.inha.hbc.ui.letter.view

import com.inha.hbc.data.remote.resp.message.UploadMessageSuccess

interface UploadMessageView {
    fun onUploadMessageSuccess(resp: UploadMessageSuccess)
    fun onUploadMessageFailure()
}