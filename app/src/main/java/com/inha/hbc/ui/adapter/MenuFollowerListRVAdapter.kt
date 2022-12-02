package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.data.remote.resp.menu.FollowerContent
import com.inha.hbc.databinding.ItemMenuFriendlistLoadingBinding
import com.inha.hbc.databinding.ItemUserListBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class MenuFollowerListRVAdapter(val friendList: ArrayList<FollowerContent?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface CstListener{
        fun onClick(pos: Int, followerContent: FollowerContent)
    }
    lateinit var cstListener: CstListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  if (viewType == 0) {
            val binding = ItemUserListBinding.inflate(
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



    class FriendHolder(val binding: ItemUserListBinding, val data: ArrayList<FollowerContent?>, val listener: CstListener): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            binding.tvItemUserListName.text = data[pos]!!.follower.name
            binding.tvItemUserListId.text = data[pos]!!.follower.username
            Glide.with(MainFragmentManager.baseActivity.applicationContext).load(data[pos]!!.follower.image_url).into(binding.ivItemUserList)

            initListener(pos, data[pos]!!)
        }

        fun initListener(pos: Int, followerContent: FollowerContent) {
            binding.root.setOnClickListener {
                listener.onClick(pos, followerContent)
            }
        }

    }

    class LoadingHolder(val binding: ItemMenuFriendlistLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

        }
    }
}