package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemMenuPartyroomBinding

class MenuPartyroomRVAdapter(): RecyclerView.Adapter<MenuPartyroomRVAdapter.PartyroomHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyroomHolder {
        val binding = ItemMenuPartyroomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PartyroomHolder(binding)
    }

    override fun onBindViewHolder(holder: PartyroomHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class PartyroomHolder(val binding: ItemMenuPartyroomBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {

        }
    }

}
