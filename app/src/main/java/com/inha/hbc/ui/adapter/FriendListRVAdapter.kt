package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemFriendListBinding

class FriendListRVAdapter(val friendList: ArrayList<String>): RecyclerView.Adapter<FriendListRVAdapter.FriendHolder>() {

    lateinit var binding: ItemFriendListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendHolder {
        binding= ItemFriendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendHolder, position: Int) {
        holder.bind(position, friendList[position])
    }

    override fun getItemCount(): Int {
        return friendList.size
    }



    class FriendHolder(val binding: ItemFriendListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int, data: String) {

        }

    }

}