package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterBinding
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterObjectListRVAdapter: RecyclerView.Adapter<LetterObjectListRVAdapter.LetterObjectListHolder>() {
    lateinit var binding: ItemLetterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterObjectListHolder {
        binding = ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterObjectListHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterObjectListHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = 2
    class LetterObjectListHolder(val binding: ItemLetterBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            if(pos == 0) {
                binding.ivItemLetter.setPadding(10, 10, 10, 10)
                binding.ivItemLetter.setImageResource(LetterFragmentManager.objectId)
            }
            else {
                binding.lavItemLetter.visibility = View.VISIBLE
                binding.lavItemLetter.setAnimation(LetterFragmentManager.letterData.animeName)
            }
        }
    }
}