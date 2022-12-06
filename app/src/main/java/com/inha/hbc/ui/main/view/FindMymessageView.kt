package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.IsMe
import com.inha.hbc.data.remote.resp.room.FindMymessageSuccess

interface FindMymessageView {
    fun onFindMymessageSuccess(resp: FindMymessageSuccess)
    fun onFindMymessageFailure()
}