package com.inha.hbc.ui.menu.ui

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMyInfoBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager

class MypageInfoHolder(val binding: ItemMyInfoBinding, val data: GetProfileSuccess): RecyclerView.ViewHolder(binding.root) {
    fun init() {
        initListener()
        initView()
    }

    fun initView() {
        Glide.with(MainFragmentManager.baseActivity.applicationContext).load(data.data.member.image_url).into(binding.ivItemMyInfoProfile)
        binding.tvItemMyInfoName.text = data.data.member.name
        val year = data.data.member.birth_date.year.toString()
        var month = data.data.member.birth_date.month.toString()
        if (month.length == 1) {
            month = "0$month"
        }
        var date = data.data.member.birth_date.date.toString()
        if (date.length == 1) {
            date = "0$date"
        }
        binding.tvItemMyInfoBirth.text = "$year.$month.$date"

        binding.tvItemMyInfoFollowing.text = data.data.followings.toString()
        binding.tvItemMyInfoFollower.text = data.data.followers.toString()

        binding.tvItemMyInfoLike.text = data.data.message_likes.toString()
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