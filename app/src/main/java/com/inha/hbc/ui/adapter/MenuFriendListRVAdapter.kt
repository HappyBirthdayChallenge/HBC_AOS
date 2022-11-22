package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.databinding.ItemMenuFriendlistLoadingBinding
import com.inha.hbc.databinding.ItemMenuListBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class MenuFriendListRVAdapter(val friendList: ArrayList<Content?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface CstListener{
        fun onClick(pos: Int)
    }
    lateinit var cstListener: CstListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  if (viewType == 0) {
            val binding = ItemMenuListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            FriendHolder(binding, friendList, cstListener)
        }
        else {
            val binding = ItemMenuFriendlistLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)

            LoadingHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendHolder) {
            holder.init(position)
        }
        else {
            (holder as LoadingHolder).bind(position)
        }
    }


    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (friendList[position] != null) {
            0
        }
        else {
            1
        }
    }



    class FriendHolder(val binding: ItemMenuListBinding, val data: ArrayList<Content?>, val listener: CstListener): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            binding.tvItemMenuTitle.text = data[pos]!!.member.name
            Glide.with(MainFragmentManager.baseActivity.applicationContext).load(data[pos]!!.member.image_url).into(binding.ivItemMenuIcon)

            initListener(pos)
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                listener.onClick(pos)
            }
        }

    }

    class LoadingHolder(val binding: ItemMenuFriendlistLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

        }
    }
}