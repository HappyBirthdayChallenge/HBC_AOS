package com.inha.hbc.ui.adapter

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemMenuFriendlistLoadingBinding
import com.inha.hbc.databinding.ItemNotifyBinding
import com.inha.hbc.ui.assist.serverDecoToId
import com.inha.hbc.ui.main.view.NotifyContent
import com.inha.hbc.ui.menu.view.AddFriendView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService

class MainNotifyRVAdapter(var data: List<NotifyContent?>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface SetNotify{
        fun onClick(pos: Int, type: String)
    }
    lateinit var setNotify: SetNotify
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val binding = ItemNotifyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NotifyHolder(binding, data, setNotify)
        } else {
            val binding = ItemMenuFriendlistLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotifyHolder) {
            holder.init(position)
        }
        else {
            (holder as LoadingHolder).init()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position] == null) {
            return 0
        }
        return 1
    }
    class NotifyHolder(val binding: ItemNotifyBinding, var data: List<NotifyContent?>, val setNotify: SetNotify):
        RecyclerView.ViewHolder(binding.root), AddFriendView {
        fun init(pos: Int) {
            initView(pos)
            initListener(pos)
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                setNotify.onClick(pos, data[pos]!!.alarm_type)
            }
            binding.tvItemNotifyFollow.setOnClickListener {
                if (data[pos]!!.friend_alarm != null)  {
                    if (binding.tvItemNotifyFollow.text == "팔로잉") {
                    }
                    else {
                        MenuRetrofitService().addFriend(data[pos]!!.friend_alarm!!.member.id.toString(), this)
                    }
                }
            }
        }

        fun initView(pos: Int) {
            when (data[pos]!!.alarm_type) {
                "FRIEND" -> {
                    binding.cvItemNotifyProfile.visibility = View.VISIBLE
                    binding.ivItemNotifyElse.visibility = View.INVISIBLE
                    binding.tvItemNotifyFollow.visibility = View.VISIBLE

                    Glide.with(MainFragmentManager.baseActivity.applicationContext)
                        .load(data[pos]!!.friend_alarm!!.member.image_url)
                        .into(binding.ivItemNotifyProfile)

                    binding.tvItemNotify.text = data[pos]!!.content

                    if (data[pos]!!.friend_alarm!!.follow) {
                        binding.tvItemNotifyFollow.text = "팔로잉"
                        binding.tvItemNotifyFollow.setTextColor(
                            MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
                        )
                        binding.tvItemNotifyFollow.textSize = 14f
                        binding.tvItemNotifyFollow.background =
                            MainFragmentManager.baseActivity.applicationContext.resources.getDrawable(
                                R.drawable.item_white_following_btn, null
                            )
                    }
                    else {
                        binding.tvItemNotifyFollow.text = "팔로우"
                            binding.tvItemNotifyFollow.background = MainFragmentManager.baseActivity.applicationContext.resources.getDrawable(
                                R.drawable.item_blue_follow_btn, null
                            )
                    }
                }
                "MESSAGE" -> {
                    binding.cvItemNotifyProfile.visibility = View.INVISIBLE
                    binding.ivItemNotifyElse.visibility = View.VISIBLE
                    binding.tvItemNotifyFollow.visibility = View.GONE

                    binding.ivItemNotifyElse.setImageResource(serverDecoToId(data[pos]!!.message_alarm!!.decoration_type))

                    binding.tvItemNotify.text = data[pos]!!.content
                }
                "MESSAGELIKE" -> {
                    binding.cvItemNotifyProfile.visibility = View.INVISIBLE
                    binding.ivItemNotifyElse.visibility = View.VISIBLE
                    binding.tvItemNotifyFollow.visibility = View.GONE

                    Glide.with(MainFragmentManager.baseActivity.applicationContext)
                        .load(data[pos]!!.message_like_alarm!!.member.image_url)
                        .into(binding.ivItemNotifyProfile)

                    binding.tvItemNotify.text = data[pos]!!.content
                }
                else -> {//ROOM
                    binding.cvItemNotifyProfile.visibility = View.INVISIBLE
                    binding.ivItemNotifyElse.visibility = View.VISIBLE
                    binding.tvItemNotifyFollow.visibility = View.GONE

                    Glide.with(MainFragmentManager.baseActivity.applicationContext)
                        .load(data[pos]!!.room_alarm!!.member.image_url)
                        .into(binding.ivItemNotifyProfile)

                    binding.tvItemNotify.text = data[pos]!!.content
                }
            }
        }

        override fun onAddFriendSuccess() {
            binding.tvItemNotifyFollow.text = "팔로잉"
            binding.tvItemNotifyFollow.setTextColor(
                MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
            )
            binding.tvItemNotifyFollow.textSize = 14f
            binding.tvItemNotifyFollow.background =
                MainFragmentManager.baseActivity.applicationContext.resources.getDrawable(
                    R.drawable.item_white_following_btn, null
                )
        }

        override fun onAddFriendFailure() {
            TODO("Not yet implemented")
        }
    }

    class LoadingHolder(val binding: ItemMenuFriendlistLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun init() {
        }
    }
}