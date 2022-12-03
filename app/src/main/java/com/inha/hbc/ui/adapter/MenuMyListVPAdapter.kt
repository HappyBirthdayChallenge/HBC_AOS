package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoData
import com.inha.hbc.databinding.ItemMyListBinding
import com.inha.hbc.databinding.ItemMyListVpBinding
import com.inha.hbc.ui.menu.ui.MymessageListVpHolder
import com.inha.hbc.ui.menu.ui.MypageListHolder
import com.inha.hbc.ui.menu.ui.MypageListVpHolder
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MenuMyListVPAdapter(val data: GetProfileSuccess): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val binding = ItemMyListVpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MypageListVpHolder(binding, data)
        } else {
            val binding = ItemMyListVpBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MymessageListVpHolder(binding, data)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MypageListVpHolder) {
            holder.init(position)
        }
        else {
            (holder as MymessageListVpHolder).init()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return if (data.data.member.id != GlobalApplication.prefs.getInfo()!!.id) {
            1
        }
        else {
            2
        }
    }
}