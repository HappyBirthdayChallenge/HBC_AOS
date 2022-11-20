package com.inha.hbc.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMainBinding
import com.inha.hbc.ui.adapter.MainPageAdapter
import com.inha.hbc.ui.letter.view.RoomInfoView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MainFragment: Fragment(), RoomInfoView {
    lateinit var binding : FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
    }

    fun initListener() {
        binding.ivMainProfileMenu.setOnClickListener {
            MainFragmentManager.transToMenu()
        }

        binding.ivMessageMenu.setOnClickListener {
        }

        binding.tvMainTitle.setOnClickListener {
            MainFragmentManager.transToLetter()
        }

    }

    fun initView() {
        val adapter = MainPageAdapter()
        binding.vpMain.adapter = adapter

        MessageRetrofitService().roomInfo(GlobalApplication.prefs.getInfo()!!.id.toString(),  this)
    }

    override fun onRoomInfoSuccess() {
    }

    override fun onRoomInfoFailure() {
        TODO("Not yet implemented")
    }
}