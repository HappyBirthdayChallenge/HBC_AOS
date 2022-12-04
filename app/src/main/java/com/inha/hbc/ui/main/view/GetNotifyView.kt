package com.inha.hbc.ui.main.view

interface GetNotifyView {
    fun onGetNotifySuccess(resp: GetNotifySuccess)
    fun onGetNotifyFailure()
}