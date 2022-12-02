package com.inha.hbc.ui.menu.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMyListBinding
import com.inha.hbc.ui.adapter.MenuMyListVPAdapter
import com.inha.hbc.util.network.menu.MenuRetrofitService

class MypageListHolder(val binding: ItemMyListBinding, val data: GetProfileSuccess): RecyclerView.ViewHolder(binding.root) {
    lateinit var adapter :MenuMyListVPAdapter
    fun init(pos: Int) {
        initVp(pos)
        initView()
    }

    fun initView() {
        val titles = listOf("파티룸", "메시지")
        TabLayoutMediator(binding.tlItemMyList, binding.vpItemMyList
        ) { tab, position -> tab.text = titles[position] }.attach()

    }

    fun initVp(pos: Int) {
        adapter = MenuMyListVPAdapter(data)
        binding.vpItemMyList.adapter = adapter
    }
}