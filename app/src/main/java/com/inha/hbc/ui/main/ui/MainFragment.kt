package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.FragmentMainBinding
import com.inha.hbc.ui.adapter.MainVPAdapter
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MainFragment: Fragment() {
    lateinit var binding : FragmentMainBinding
    var pageData = ArrayList<Int>()//0 왼쪽 1 가운데 2 내용 3 오른쪽
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

        binding.ivMainSend.setOnClickListener {
        }

        binding.tvMainTitle.setOnClickListener {
            MainFragmentManager.transToLetter()
        }

    }

    fun initView() {
        pageData.apply{
            add(0)
            add(1)
            add(3)
        }
        val adapter = MainVPAdapter(pageData)
        binding.vpMain.adapter = adapter
        binding.vpMain.currentItem = 1


        binding.tvMainTitle.text = GlobalApplication.prefs.getInfo()!!.name
    }
}