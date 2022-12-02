package com.inha.hbc.ui.menu.ui

import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemMyInfoBinding
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager

class MypageInfoHolder(val binding: ItemMyInfoBinding): RecyclerView.ViewHolder(binding.root) {
    fun init() {
        initListener()
    }

    fun initListener() {
        binding.tvItemMyInfoFollower.setOnClickListener {
            MenuFragmentManager.openFriendList(1)
        }
        binding.tvItemMyInfoFollowerTitle.setOnClickListener {
            MenuFragmentManager.openFriendList(1)
        }
        binding.tvItemMyInfoFollowing.setOnClickListener {
            MenuFragmentManager.openFriendList(0)
        }
        binding.tvItemMyInfoFollowingTitle.setOnClickListener {
            MenuFragmentManager.openFriendList(0)
        }
    }
}