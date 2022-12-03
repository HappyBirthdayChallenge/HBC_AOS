package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.data.remote.resp.room.ReceiveMessageContent
import com.inha.hbc.databinding.ItemMenuFriendlistLoadingBinding
import com.inha.hbc.databinding.ItemMenuMessageBinding

class MainMymessageRVAdapter(var dataArr: List<ReceiveMessageContent?>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    interface SetReceiveMessage{
        fun onClick(pos: Int)
    }
    lateinit var setReceiveMessage: SetReceiveMessage
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            val binding = ItemMenuMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceiveMessageHolder(binding, setReceiveMessage)
        } else {
            val binding = ItemMenuFriendlistLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MenuFollowingListRVAdapter.LoadingHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReceiveMessageHolder) {
            holder.init(position)
        }
        else {
            (holder as MenuFollowingListRVAdapter.LoadingHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataArr[position] != null) {
            1
        }
        else {
            0
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ReceiveMessageHolder(val binding: ItemMenuMessageBinding, val setReceiveMessage: SetReceiveMessage): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initListener(pos)
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                setReceiveMessage.onClick(pos)
            }
        }
    }
}