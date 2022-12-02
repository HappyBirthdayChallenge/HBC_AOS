package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMyInfoBinding
import com.inha.hbc.databinding.ItemMyListBinding
import com.inha.hbc.ui.menu.ui.MypageInfoHolder
import com.inha.hbc.ui.menu.ui.MypageListHolder

class MypageRVAdapter(val data: GetProfileSuccess): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding =
                ItemMyListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MypageListHolder(binding, data)
        }
        else {
            val binding = ItemMyInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MypageInfoHolder(binding, data)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MypageListHolder) {
            holder.init(position)
        }
        else (holder as MypageInfoHolder).init()
    }

    override fun getItemCount(): Int = 2

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return 0
        return 1
    }

}