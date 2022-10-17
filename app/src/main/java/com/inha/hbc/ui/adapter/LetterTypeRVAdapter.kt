package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.inha.hbc.databinding.ItemLetterEtBinding
import com.inha.hbc.databinding.ItemLetterIvBinding

class LetterTypeRVAdapter(val viewData: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return viewData[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            0 -> {
                val binding = ItemLetterEtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TextHolder(binding)
            }
            else -> {
                val binding = ItemLetterIvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (viewData[position] == 0) {
            (holder as TextHolder).bind(position)
        }
        else {
            (holder as ImageHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return viewData.size
    }



    class ImageHolder(val binding: ItemLetterIvBinding): ViewHolder(binding.root) {
        fun bind(pos: Int) {

        }
    }

    class TextHolder(val binding: ItemLetterEtBinding): ViewHolder(binding.root) {
        fun bind(pos: Int) {

        }
    }

}