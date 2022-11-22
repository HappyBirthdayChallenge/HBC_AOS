package com.inha.hbc.util.fragmentmanager

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.local.LetterData
import com.inha.hbc.data.remote.resp.message.CreateMessageSuccess
import com.inha.hbc.ui.adapter.LetterMediaListRVAdapter
import com.inha.hbc.ui.letter.ui.*
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.util.network.message.MessageRetrofitService

object LetterFragmentManager: CreateMessageView {
    lateinit var manager: FragmentManager
    lateinit var mainPage: MainFragment
    lateinit var letterBaseFragment: LetterBaseFragment
    lateinit var letterFragment: LetterFragment
    var frameId = 0

    var objectPageType = 0
    var letterData = LetterData("", "", "")
    var objectId = R.drawable.img_deco_drink_1
    var viewWidth = 0

    lateinit var mediaAdapter: LetterMediaListRVAdapter


    var pathArr = ArrayList<String>()
    var uriArr = ArrayList<Uri>()
    var typeArr = ArrayList<Int>() // 0 사진 1 동영상 2 음성
    var letterId = 0

    fun init(manager: FragmentManager, mainPage: MainFragment, id: Int) {
        this.manager = manager
        this.mainPage = mainPage
        this.frameId = id
        letterData.animeName = "json_deco_anime_1.json"
    }

    fun start(roomId: String) {
        letterBaseFragment = LetterBaseFragment()

        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().add(frameId, letterBaseFragment).commit()

        MessageRetrofitService().createMessage(roomId, this)
    }

    fun letterClose() {
        pathArr.clear()
        uriArr.clear()
        typeArr.clear()
        manager.beginTransaction().remove(letterBaseFragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    fun objectOpen(type: String) {
        objectPageType = when(type) {
            "img_deco_gift_" -> 0
            "img_deco_drink_" -> 1
            "img_deco_toy_" -> 2
            "img_pic_" -> 3
            else -> 4//음식
        }

        manager.beginTransaction().add(MainFragmentManager.id, ObjectSelectionFragment()).commit()
    }

    fun objectClose(page: Fragment, selected: Boolean) {
        MainFragmentManager.manager.beginTransaction().remove(page).commit()
        if (selected) {
            letterBaseFragment.getObject()
        }
    }

    fun animeSelected(title: String) {
        letterData.animeName = title
        letterBaseFragment.getAnime()
    }

    fun openRecording() {
        manager.beginTransaction().add(MainFragmentManager.id, LetterRecordFragment()).commit()
    }

    fun recordClose(uri: Uri, page: Fragment) {
        uriArr.add(uri)
        typeArr.add(2)
        pathArr.add("")
        mediaAdapter.notifyItemInserted(uriArr.size)
        manager.beginTransaction().remove(page).commit()

        letterId = -1
    }

    fun recordClose(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
    }

    fun updateData(uri: Uri, type: Int, path: String) {
        uriArr.add(uri)
        typeArr.add(type)
        pathArr.add(path)
        mediaAdapter.notifyItemInserted(uriArr.size)
    }

    fun openShow(pos: Int) {
        manager.beginTransaction().add(frameId, LetterShowFragment(pos - 1)).commit()
    }

    fun closeShow(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
    }

    override fun onCreateMessageSuccess(resp: CreateMessageSuccess) {
        letterId = resp!!.data.message_id
    }

    override fun onCreateMessageFailure() {
        manager.beginTransaction().remove(letterBaseFragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }


}