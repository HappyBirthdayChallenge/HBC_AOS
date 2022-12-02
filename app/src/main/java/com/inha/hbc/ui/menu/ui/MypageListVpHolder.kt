package com.inha.hbc.ui.menu.ui

import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoData
import com.inha.hbc.databinding.ItemMyListVpBinding
import com.inha.hbc.ui.adapter.MenuPartyroomRVAdapter
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager

class MypageListVpHolder(val binding: ItemMyListVpBinding, val data: GetProfileSuccess): RecyclerView.ViewHolder(binding.root) {
    lateinit var adapter: MenuPartyroomRVAdapter
    var listSize = 0
    var page = 0
    var initV = true

    fun init(pos: Int) {
        initRv(pos)
    }
    fun initRv(pos: Int) {
        adapter = MenuPartyroomRVAdapter(data)
        adapter.setPartyroomRv = object: MenuPartyroomRVAdapter.SetPartyroomRv {
            override fun onClick(pos: Int) {
                MenuFragmentManager.goPartyRoom(data)
            }
        }
        binding.rvItemMyListVp.adapter = adapter

        val manager = FlexboxLayoutManager(MenuFragmentManager.menuPage.context)
        manager.alignItems = AlignItems.FLEX_START
        binding.rvItemMyListVp.layoutManager = manager
    }
}