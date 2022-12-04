package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemMenuFriendlistLoadingBinding
import com.inha.hbc.databinding.ItemNotifyBinding
import com.inha.hbc.ui.main.view.NotifyContent

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

        }
    }

    class LoadingHolder(val binding: ItemMenuFriendlistLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun init() {

        }
    }
}