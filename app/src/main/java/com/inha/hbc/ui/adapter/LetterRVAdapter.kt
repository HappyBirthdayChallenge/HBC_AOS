package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.ItemLetterBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterRVAdapter(var idData: ArrayList<String>): RecyclerView.Adapter<LetterRVAdapter.LetterHolder>() {
    lateinit var binding: ItemLetterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterHolder {
        binding = ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterHolder(binding, idData)
    }

    override fun onBindViewHolder(holder: LetterHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = idData.size

    class LetterHolder(val binding: ItemLetterBinding, var idData: ArrayList<String>): RecyclerView.ViewHolder(binding.root) {
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
                binding.ivItemLetter.setImageURI(idData[pos].toUri())
            }
        }
    }
}