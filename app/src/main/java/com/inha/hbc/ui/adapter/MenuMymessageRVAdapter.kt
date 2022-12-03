package com.inha.hbc.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemMenuMessageBinding

class MenuMymessageRVAdapter(var dataList): RecyclerView.Adapter<MenuMymessageRVAdapter.MymessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MymessageHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MymessageHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MymessageHolder(val binding: ItemMenuMessageBinding): RecyclerView.ViewHolder(binding.root) {

    }
}