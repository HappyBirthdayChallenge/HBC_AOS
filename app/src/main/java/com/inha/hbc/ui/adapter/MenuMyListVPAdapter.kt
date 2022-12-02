package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoData
import com.inha.hbc.databinding.ItemMyListBinding
import com.inha.hbc.databinding.ItemMyListVpBinding
import com.inha.hbc.ui.menu.ui.MypageListHolder
import com.inha.hbc.ui.menu.ui.MypageListVpHolder

class MenuMyListVPAdapter(val data: GetProfileSuccess): RecyclerView.Adapter<MypageListVpHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypageListVpHolder {
        val binding = ItemMyListVpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MypageListVpHolder(binding, data)
    }

    override fun onBindViewHolder(holder: MypageListVpHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = 2
}