package com.inha.hbc.ui.letter.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.inha.hbc.databinding.FragmentLetterBinding
import com.inha.hbc.ui.adapter.LetterVPAdapter
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterFragment(val binding: FragmentLetterBinding): RecyclerView.ViewHolder(binding.root) {

    lateinit var adapter: LetterVPAdapter
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
        adapter = LetterVPAdapter()
        binding.vpLetter.adapter = adapter
        TabLayoutMediator(binding.tlLetter, binding.vpLetter) { tab, position -> tab.text =
        if (position == 0) {
            "장식 및 효과"
        }
        else {
            "미디어"
        }}.attach()

        binding.vpLetter.isUserInputEnabled = false
    }

    fun initListener() {

    }

    fun notifyUpdate() {
        checkPermission(PERMISSIONS, PERMISSIONS_REQUEST)
        adapter.notifyItemRangeChanged(0, 2)
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