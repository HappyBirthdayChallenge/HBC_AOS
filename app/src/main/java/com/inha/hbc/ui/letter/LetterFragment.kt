package com.inha.hbc.ui.letter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.inha.hbc.databinding.FragmentLetterBinding
import com.inha.hbc.ui.adapter.LetterMenuRVAdapter
import com.inha.hbc.ui.adapter.LetterObjectRVAdapter
import com.inha.hbc.ui.adapter.LetterRVAdapter
import com.inha.hbc.ui.dialog.LetterDialog
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.io.File
import java.util.Date

class LetterFragment(val binding: FragmentLetterBinding): RecyclerView.ViewHolder(binding.root) {

    var rvArr = ArrayList<String>()
    lateinit var adapter: LetterRVAdapter
    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    )
    val PERMISSIONS_REQUEST = 100

    fun bind() {

        initListener()
        initView()


    }


    fun initView() {
        rvArr.apply {
            add("")
            add("")
        }
        adapter = LetterRVAdapter(rvArr)
        binding.rvLetterWriteMedia.adapter = adapter
    }

    fun initListener() {

        binding.fabLetterAdd.setOnClickListener {
            binding.fabLetterAdd.hide()
            binding.fabLetterSend.hide()

            MainFragmentManager.letterBaseFragment.openList()
        }

        binding.fabLetterSend.setOnClickListener {

        }

    }

    fun notifyUpdate() {
        checkPermission(PERMISSIONS, PERMISSIONS_REQUEST)
        adapter.notifyItemRangeChanged(0, 2)
    }

    fun updateData(uri: Uri) {
        rvArr.add(uri.toString())
        adapter.notifyItemInserted(rvArr.size)
    }

    fun btnVisible(hidden: Boolean) {
        if (hidden) {
            binding.fabLetterSend.hide()
            binding.fabLetterAdd.hide()
        }
        else {
            binding.fabLetterSend.show()
            binding.fabLetterAdd.show()
        }
    }

    fun checkPermission(permissions: Array<String>, flag: Int):Boolean {
        val unPremList = ArrayList<String>()
        for (i in permissions) {
            if (MainFragmentManager.baseActivity.checkSelfPermission(i) != PackageManager.PERMISSION_GRANTED) {
                unPremList.add(i)
            }
        }
        if (unPremList.isNotEmpty()) {
            MainFragmentManager.baseActivity.requestPermissions(unPremList.toTypedArray(), PERMISSIONS_REQUEST)
            return false
        }
        return true
    }




}