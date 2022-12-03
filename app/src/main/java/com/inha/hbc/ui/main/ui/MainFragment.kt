package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
import androidx.viewpager2.widget.ViewPager2.OffscreenPageLimit
import com.bumptech.glide.Glide
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
    var loadComplete = false
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

        binding.vpMain.viewTreeObserver.addOnGlobalLayoutListener (object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (binding.tvMainTitle.text != "someone name" && loadComplete) {
                    MainFragmentManager.baseActivity.binding.lavMainActivityLoading.visibility = View.GONE
                    loadComplete = false
                    binding.vpMain
                        .viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        })
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
            if (binding.tvMainTitle.text == GlobalApplication.prefs.getInfo()!!.username) {
                MainFragmentManager.transToMessageList()
            }
            else {
                MainFragmentManager.transToLetter()
            }
        }
        binding.ivMainNotify.setOnClickListener {
            MainFragmentManager.
        }

        binding.tvMainTitle.setOnClickListener {
        }

    }

    fun initView() {
        binding.lavMainLoading.visibility = View.VISIBLE
        RoomRetrofitService().searchDeco(1.toString(), MainFragmentManager.roomId.toString(), this)

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
        binding.vpMain.offscreenPageLimit = 2
        binding.vpMain.currentItem = 1


        Glide.with(MainFragmentManager.baseActivity.applicationContext).load(GlobalApplication.prefs.getInfo()!!.image_url).into(binding.ivMainProfileMenu)


        binding.tvMainTitle.text = MainFragmentManager.personInfo.data!!.username


        if (binding.tvMainTitle.text == GlobalApplication.prefs.getInfo()!!.username) {
            binding.ivMainSend.setImageResource(R.drawable.ic_main_message_list)
        }
        else {
            binding.ivMainSend.setImageResource(R.drawable.ic_main_send)
        }

        binding.lavMainLoading.visibility = View.GONE
        loadComplete = true

    }

    override fun onSearchDecoFailure() {
        TODO("Not yet implemented")
    }
}