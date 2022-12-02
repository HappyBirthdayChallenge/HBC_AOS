package com.inha.hbc.util.fragmentmanager

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.data.remote.resp.message.GetMessageSuccess
import com.inha.hbc.ui.letter.ui.LetterReadFragment
import com.inha.hbc.ui.letter.ui.LetterReadObjectFragment
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.ui.main.view.GetMessageView
import com.inha.hbc.util.network.message.MessageRetrofitService

object LetterReadManager: GetMessageView {
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    lateinit var letterPage: LetterReadFragment
    var frameId = 0
    var senderId = ""

    fun init(manager: FragmentManager, mainPage: MainFragment, id: Int) {
        this.manager = manager
        this.mainPage = mainPage
        this.frameId = id
        letterPage = LetterReadFragment()
        this.manager.beginTransaction().add(frameId, letterPage).commit()
        this.manager.beginTransaction().hide(letterPage).commit()
    }

    fun start(messageId: String) {
        MessageRetrofitService().getMessage(messageId, this)
        Log.d("fragStart", "FragStart1")
    }

    override fun onGetMessageSuccess(resp: GetMessageSuccess) {
        letterPage.init(resp)
        senderId = resp.data!!.writer.name
        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().show(letterPage).commit()
    }

    override fun onGetMessageFailure() {
        TODO("Not yet implemented")
    }

    fun closeShow(view: Fragment) {
        manager.beginTransaction().show(letterPage).commit()
        manager.beginTransaction().remove(view).commit()
    }

    fun openShow(url: String, type: Int) {
        manager.beginTransaction().add(frameId, LetterReadObjectFragment(url, type)).commit()
        manager.beginTransaction().hide(letterPage).commit()
    }

    fun openTextDetail(txt: String) {
        manager.beginTransaction().add(frameId, LetterReadObjectFragment(txt, 3)).commit()
        manager.beginTransaction().hide(letterPage).commit()
    }

    fun goMain() {
        manager.beginTransaction().remove(letterPage).commit()
        manager.beginTransaction().show(mainPage).commit()
    }
}