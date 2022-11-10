package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemMainPageBinding

class MainPageAdapter(): RecyclerView.Adapter<MainPageAdapter.PageHolder>() {

    lateinit var binding: ItemMainPageBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        binding = ItemMainPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageHolder(binding)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 3

    class PageHolder(val binding: ItemMainPageBinding): RecyclerView.ViewHolder(binding.root) {

        val bgOptions = RequestOptions().fitCenter()
            .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

        fun bind(pos: Int) {
            if (pos == 0) {
                Glide.with(itemView).load(R.drawable.bg_main_left).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else if (pos == 1) {
                Glide.with(itemView).load(R.drawable.bg_main_front).apply(bgOptions).into(binding.ivItemMainBg)
            }
            else if (pos == 2) {
                Glide.with(itemView).load(R.drawable.bg_main_right).apply(bgOptions).into(binding.ivItemMainBg)
            }
        }
    }
}
