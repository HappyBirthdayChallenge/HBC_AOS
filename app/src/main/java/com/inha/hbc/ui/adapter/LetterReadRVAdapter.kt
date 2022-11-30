package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemLetterReadBinding

class LetterReadRVAdapter(val mediaArr: List<String>): RecyclerView.Adapter<LetterReadRVAdapter.MediaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder {
        val binding = ItemLetterReadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = mediaArr.size

    class MediaHolder(val binding :ItemLetterReadBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
        }

    }
}