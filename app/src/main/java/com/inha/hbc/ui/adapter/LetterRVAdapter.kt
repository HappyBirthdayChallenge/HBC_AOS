package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemLetterBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterRVAdapter(var idData: ArrayList<String>): RecyclerView.Adapter<LetterRVAdapter.LetterHolder>() {
    lateinit var binding: ItemLetterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterHolder {
        binding = ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = idData.size

    class LetterHolder(val binding: ItemLetterBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            if(pos == 0) {
                binding.ivItemLetter.setImageResource(MainFragmentManager.objectId)
                binding.ivItemLetter.setPadding(10, 10, 10, 10)
            }
            else if (pos == 1) {
                binding.ivItemLetter.visibility = View.GONE
                binding.lavItemLetter.apply {
                    visibility = View.VISIBLE
                    setAnimation(MainFragmentManager.letterData.animeId)
                }
            }
            else {
                //사진이나 동영상썸네일이나 음성파일모양
            }
        }
    }
}