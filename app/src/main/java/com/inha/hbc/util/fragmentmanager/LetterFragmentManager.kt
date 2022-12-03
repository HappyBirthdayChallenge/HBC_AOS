package com.inha.hbc.util.fragmentmanager

import android.net.Uri
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.R
import com.inha.hbc.data.local.FileInfo
import com.inha.hbc.data.local.LetterData
import com.inha.hbc.data.remote.req.message.MessageData
import com.inha.hbc.data.remote.resp.message.CreateMessageSuccess
import com.inha.hbc.data.remote.resp.message.UploadSuccess
import com.inha.hbc.ui.adapter.LetterMediaListRVAdapter
import com.inha.hbc.ui.assist.convertAnime
import com.inha.hbc.ui.assist.convertDeco
import com.inha.hbc.ui.letter.ui.*
import com.inha.hbc.ui.letter.view.CreateMessageView
import com.inha.hbc.ui.letter.view.UploadView
import com.inha.hbc.ui.main.ui.MainFragment
import com.inha.hbc.util.network.message.MessageRetrofitService

object LetterFragmentManager: CreateMessageView, UploadView {
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


    var fileInfo = ArrayList<FileInfo>()
    var letterId = 0
    var fileId = 0

    fun init(manager: FragmentManager, mainPage: MainFragment, id: Int) {
        this.manager = manager
        this.mainPage = mainPage
        this.frameId = id
        letterData.animeName = "json_deco_anime_1.json"
        fileId++
    }

    fun start(roomId: String) {
        letterBaseFragment = LetterBaseFragment()

        manager.beginTransaction().hide(mainPage).commit()
        manager.beginTransaction().add(frameId, letterBaseFragment).commit()

        MessageRetrofitService().createMessage(roomId, this)
    }

    fun letterClose() {
        fileInfo.clear()
        manager.beginTransaction().remove(letterBaseFragment).commit()
        manager.beginTransaction().show(mainPage).commit()

        letterId = -1
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

    fun recordClose(path: String, uri: Uri, page: Fragment) {
        fileId++
        fileInfo.add(FileInfo(path, uri, 2, fileId, false, null))
        mediaAdapter.notifyItemInserted(fileInfo.size)
        uploadFile(2)
        manager.beginTransaction().remove(page).commit()
    }

    fun recordClose(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
    }

    fun updateData(uri: Uri, type: Int, path: String) {
        fileId++
        fileInfo.add(FileInfo(path, uri, type, fileId, false, null))
        uploadFile(type)
        mediaAdapter.notifyItemInserted(fileInfo.size)
    }

    fun uploadFile(type: Int) {
        when (type) {
            2 -> {//음성 2
                MessageRetrofitService().audioUpload(fileInfo[fileInfo.size - 1].path, letterId, fileId, this)
            }
            0 -> {//사진 0
                MessageRetrofitService().imgUpload(fileInfo[fileInfo.size - 1].path, letterId, fileId, this)
            }
            else -> {//동영상 1
                MessageRetrofitService().videoUpload(fileInfo[fileInfo.size - 1].path, letterId, fileId, this)
            }
        }
    }
    fun openShow(pos: Int) {
        manager.beginTransaction().add(frameId, LetterShowFragment(pos - 1)).commit()
    }

    fun closeShow(view: Fragment) {
        manager.beginTransaction().remove(view).commit()
    }

    fun getMessageData(): MessageData? {
        var fileIds = ArrayList<Int>()
        for (i in fileInfo) {
            if (!i.success) {
                Toast.makeText(letterBaseFragment.context, "파일업로드가 끝난 후 버튼을 눌러주세요!", Toast.LENGTH_SHORT).show()
                return null
            }
            fileIds.add(i.realId!!.toInt())
        }
        letterData.letterText = letterFragment.binding.etLetter.text.toString()
        return MessageData(convertAnime(letterData.animeName), letterData.letterText, convertDeco(letterData.objectName), fileIds.toList(), letterId)
    }

    override fun onCreateMessageSuccess(resp: CreateMessageSuccess) {
        letterId = resp!!.data.message_id
    }

    override fun onCreateMessageFailure() {
        manager.beginTransaction().remove(letterBaseFragment).commit()
        manager.beginTransaction().show(mainPage).commit()
    }

    override fun onUploadSuccess(resp: UploadSuccess) {
        var arrPos = 1
        for (i in fileInfo) {
            if(resp.data!!.client_id == i.id) {
                i.success = true
                i.realId = resp.data.file_id.toString()
                break
            }
            arrPos++
        }

        mediaAdapter.notifyItemChanged(arrPos)
    }

    override fun onUploadFailure() {
        TODO("Not yet implemented")
    }


}