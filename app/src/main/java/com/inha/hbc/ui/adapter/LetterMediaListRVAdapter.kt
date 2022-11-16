package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterMediaListRVAdapter(): RecyclerView.Adapter<LetterMediaListRVAdapter.LetterHolder>() {
    lateinit var binding: ItemLetterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterHolder {
        binding = ItemLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LetterHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = MainFragmentManager.uriArr.size + 1

    class LetterHolder(val binding: ItemLetterBinding): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            initListener(pos)
            if(pos == 0) {
                binding.tvItemLetterTitle.visibility = View.VISIBLE
                binding.ivItemLetterAttach.visibility = View.VISIBLE
            }
            else {
                binding.ivItemLetterDel.visibility = View.VISIBLE
                val uri = MainFragmentManager.uriArr[pos - 1]
                when(MainFragmentManager.typeArr[pos - 1]) { // 0 사진 1 동영상 2 음성
                    0 -> {
                        binding.ivItemLetter.setImageURI(uri)
                    }
                    1 -> {

                    }
                    else -> {
                        binding.ivItemLetterAttach.setImageResource(R.drawable.ic_letter_menu_record)
                        binding.ivItemLetterAttach.visibility = View.VISIBLE
                    }
                }
            }
        }

        fun initListener(pos: Int) {
            binding.cvItemLetter.setOnClickListener {
                when (pos) {
                    0 -> {
                            MainFragmentManager.letterBaseFragment.openList()
                    }
                    else -> {

                    }
                }
            }

            binding.ivItemLetterDel.setOnClickListener {
                MainFragmentManager.typeArr.removeAt(pos- 1)
                MainFragmentManager.uriArr.removeAt(pos - 1)

                MainFragmentManager.mediaAdapter.notifyDataSetChanged()
            }
        }
    }
}