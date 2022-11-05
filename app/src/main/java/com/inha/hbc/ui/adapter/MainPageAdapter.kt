package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemMainPageBinding

class MainPageAdapter(): RecyclerView.Adapter<MainPageAdapter.PageHolder>() {

    lateinit var binding: ItemMainPageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        binding = ItemMainPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageHolder(binding)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    class PageHolder(val binding: ItemMainPageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            Glide.with(itemView).load(R.drawable.main_front).centerCrop().into(binding.ivItemMainBg)
        }
    }
}