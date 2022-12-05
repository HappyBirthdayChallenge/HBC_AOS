package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.IsMe

interface FindMymessageView {
    fun onFindMymessageSuccess(resp: IsMe)
    fun onFindMymessageFailure()
}