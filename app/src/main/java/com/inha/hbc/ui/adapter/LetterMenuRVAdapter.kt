package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterMenuBinding

class LetterMenuRVAdapter(val menuData: ArrayList<String>): RecyclerView.Adapter<LetterMenuRVAdapter.LetterMenuHolder>() {
    interface OnListener {
        fun onClick(pos: Int)
    }
    lateinit var onlistener: OnListener
    lateinit var binding: ItemLetterMenuBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterMenuHolder {
        binding = ItemLetterMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterMenuHolder(binding, onlistener, menuData)
    }

    override fun onBindViewHolder(holder: LetterMenuHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return menuData.size
    }



    class LetterMenuHolder(val binding: ItemLetterMenuBinding, val onlistener: OnListener, val menuData: ArrayList<String>): ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.tvItemLetterMenu.text = menuData[pos]
            binding.ivItemLetterMenu.setImageResource(
                when(pos) {
                    0 -> R.drawable.ic_letter_menu_camera
                    1 -> R.drawable.ic_letter_menu_video
                    2 -> R.drawable.ic_letter_menu_gallary
                    else -> R.drawable.ic_letter_menu_record
                }
            )
            initListener(pos)
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                onlistener.onClick(pos)
            }
        }
    }
}