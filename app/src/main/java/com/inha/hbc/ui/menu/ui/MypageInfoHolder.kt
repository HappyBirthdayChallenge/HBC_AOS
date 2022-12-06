package com.inha.hbc.ui.menu.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMyInfoBinding
import com.inha.hbc.ui.assist.dpToPx
import com.inha.hbc.ui.menu.view.AddFriendView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MypageInfoHolder(val binding: ItemMyInfoBinding, val data: GetProfileSuccess):
    RecyclerView.ViewHolder(binding.root), AddFriendView{
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

        if (data.data.member.id == GlobalApplication.prefs.getInfo()!!.id) {
            binding.cvItemMyInfoFollow.visibility = View.GONE
        }
        else {
            binding.cvItemMyInfoFollow.visibility = View.VISIBLE
            if (data.data.follow) {
                binding.tvItemMyInfoFollow.text = "팔로잉"
                binding.ivItemMyInfoFollow.visibility= View.VISIBLE
                binding.cvItemMyInfoFollow.strokeColor =
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.white, null)
                binding.cvItemMyInfoFollow.strokeWidth = dpToPx(0)
                binding.tvItemMyInfoFollow.setTextColor(
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
                )
            }
            else {
                binding.tvItemMyInfoFollow.text = "팔로우"
                binding.ivItemMyInfoFollow.visibility= View.GONE
                binding.cvItemMyInfoFollow.strokeColor =
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.blue, null)
                binding.cvItemMyInfoFollow.strokeWidth = dpToPx(2)
                binding.tvItemMyInfoFollow.setTextColor(
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.blue, null)
                )
            }
        }



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

        binding.cvItemMyInfoFollow.setOnClickListener {
            if (binding.tvItemMyInfoFollow.text == "팔로우") {
                MenuRetrofitService().addFriend(data.data.member.id.toString(), this)
            }
        }
    }

    override fun onAddFriendSuccess() {
        binding.tvItemMyInfoFollow.text = "팔로잉"
        binding.ivItemMyInfoFollow.visibility= View.VISIBLE
        binding.cvItemMyInfoFollow.strokeColor =
            MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.white, null)
        binding.cvItemMyInfoFollow.strokeWidth = dpToPx(0)
        binding.tvItemMyInfoFollow.setTextColor(
            MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
        )
    }

    override fun onAddFriendFailure() {
        TODO("Not yet implemented")
    }

}