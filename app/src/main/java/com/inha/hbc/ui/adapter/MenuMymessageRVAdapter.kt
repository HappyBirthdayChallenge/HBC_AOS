package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.databinding.ItemMenuMessageBinding
import com.inha.hbc.ui.assist.serverDecoToId
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MenuMymessageRVAdapter(var dataList: List<Content?>): RecyclerView.Adapter<MenuMymessageRVAdapter.MymessageHolder>() {

    interface SetMymessage{
        fun onClick(pos: Int)
    }
    lateinit var setMymessage: SetMymessage
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MymessageHolder {
        val binding = ItemMenuMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MymessageHolder(binding, setMymessage, dataList)
    }

    override fun onBindViewHolder(holder: MymessageHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MymessageHolder(val binding: ItemMenuMessageBinding, val setMymessage: SetMymessage, var data: List<Content?>): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initListener(pos)
            initView(pos)
        }

        fun initView(pos: Int) {
            binding.ivItemMenuMessageObject.setImageResource(serverDecoToId(data[pos]!!.decoration_type))
            binding.tvItemMenuMessage.text = "To. ${data[pos]!!.room_owner.username}"
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                setMymessage.onClick(pos)
            }
        }
    }
}