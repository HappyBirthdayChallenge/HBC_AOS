package com.inha.hbc.ui.letter.view

import com.inha.hbc.data.remote.resp.message.UploadSuccess

interface UploadView {
    fun onAudioUploadSuccess(resp: UploadSuccess)
    fun onAudioUploadFailure()
}