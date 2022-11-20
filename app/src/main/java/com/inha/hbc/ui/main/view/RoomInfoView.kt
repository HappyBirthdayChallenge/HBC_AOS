package com.inha.hbc.ui.main.view

import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess

interface RoomInfoView {
    fun onRoomInfoSuccess(resp: RoomInfoSuccess)
    fun onRoomInfoFailure()
}