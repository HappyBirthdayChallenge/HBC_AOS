package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.FragmentLetterListBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterVPAdapter: RecyclerView.Adapter<LetterVPAdapter.LetterListHolder>() {

    lateinit var binding: FragmentLetterListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterListHolder {
        binding = FragmentLetterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterListHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterListHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int =2

    class LetterListHolder(val binding: FragmentLetterListBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            binding.rvLetterListMedia.adapter =
            if (pos == 0) {
                LetterObjectListRVAdapter()
            }
            else {
                MainFragmentManager.mediaAdapter = LetterMediaListRVAdapter()
                MainFragmentManager.mediaAdapter
            }
        }
    }
}