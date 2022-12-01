package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
import androidx.viewpager2.widget.ViewPager2.OffscreenPageLimit
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.data.remote.resp.message.SearchDecoSuccess
import com.inha.hbc.databinding.FragmentMainBinding
import com.inha.hbc.ui.adapter.MainVPAdapter
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.ui.main.view.SearchDecoView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.network.room.RoomRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MainFragment: Fragment(), SearchDecoView {
    lateinit var binding : FragmentMainBinding
    var pageData = ArrayList<Int>()//0 왼쪽 1 가운데 2 내용 3 오른쪽
    var where = "mine"
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

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.tvMainTitle.text = MainFragmentManager.personInfo.data!!.username
        initView()
    }

    fun initListener() {
        binding.ivMainProfileMenu.setOnClickListener {
            MainFragmentManager.transToMenu()
        }

        binding.ivMainSend.setOnClickListener {
            MainFragmentManager.transToLetter()
        }

        binding.tvMainTitle.setOnClickListener {
        }

    }

    fun initView() {
        binding.lavMainLoading.visibility = View.VISIBLE
        RoomRetrofitService().searchDeco(1.toString(), MainFragmentManager.roomId.toString(), this)

        if (where == "mine") {
            binding.ivMainSend.setImageResource(R.drawable.ic_main_message_list)
        }
    }

    override fun onSearchDecoSuccess(resp: SearchDecoSuccess) {
        pageData.clear()

        pageData.apply{
            add(0)//왼
            add(1)//메인
        }

        for (i in 0 until resp.data!!.total_pages) {
            pageData.add(2)
        }

        pageData.add(3)

        val adapter = MainVPAdapter(pageData)
        binding.vpMain.adapter = adapter
        binding.vpMain.offscreenPageLimit = OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.vpMain.currentItem = 1


        binding.tvMainTitle.text = MainFragmentManager.personInfo.data!!.username

        binding.lavMainLoading.visibility = View.GONE
    }

    override fun onSearchDecoFailure() {
        TODO("Not yet implemented")
    }
}