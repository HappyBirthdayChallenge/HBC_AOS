package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.databinding.ItemMenuFriendlistLoadingBinding
import com.inha.hbc.databinding.ItemNotifyBinding
import com.inha.hbc.ui.assist.serverDecoToId
import com.inha.hbc.ui.main.view.NotifyContent
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class MainNotifyRVAdapter(var data: List<NotifyContent?>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface SetNotify{
        fun onClick(pos: Int)
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
    class NotifyHolder(val binding: ItemNotifyBinding, var data: List<NotifyContent?>, val setNotify: SetNotify): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            when (data[pos]!!.alarm_type) {
                "FRIEND" -> {
                    binding.cvItemNotifyProfile.visibility = View.VISIBLE
                    binding.ivItemNotifyElse.visibility = View.INVISIBLE
                    binding.tvItemNotifyFollow.visibility = View.VISIBLE

                    Glide.with(MainFragmentManager.baseActivity.applicationContext)
                        .load(data[pos]!!.friend_alarm!!.member.image_url)
                        .into(binding.ivItemNotifyProfile)

                    binding.tvItemNotify.text = data[pos]!!.content
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
    }

    class LoadingHolder(val binding: ItemMenuFriendlistLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun init() {
        }
    }
}