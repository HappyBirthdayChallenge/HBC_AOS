package com.inha.hbc.ui.adapter

import android.R.drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignSelf
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.R
import com.inha.hbc.databinding.ItemLetterObjectBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class LetterObjectRVAdapter(pos: Int): RecyclerView.Adapter<LetterObjectRVAdapter.ObjectHolder>() {

    interface Clistener {
        fun onClick(title: String)
    }
    lateinit var clistener: Clistener

    lateinit var binding: ItemLetterObjectBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectHolder {
        binding = ItemLetterObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObjectHolder(binding)
    }

    override fun onBindViewHolder(holder: ObjectHolder, position: Int) {
        holder.bind(position)
        holder.initListener(clistener, position)
    }

    override fun getItemCount(): Int = 5

    class ObjectHolder(val binding: ItemLetterObjectBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {

            val lp = binding.root.layoutParams
            (lp as FlexboxLayoutManager.LayoutParams).flexBasisPercent = "0.5".toFloat()
            lp.height = MainFragmentManager.viewWidth / 2

            when (pos) {
                0 -> {
                    binding.ivItemLetterObject.setImageResource(R.drawable.img_deco_gift_1)
                    binding.tvItemLetterTitle.text = "선물상자"
                    binding.tvItemLetterCount.text = "10"
                }
                1 -> {
                    binding.ivItemLetterObject.setImageResource(R.drawable.img_deco_drink_1)
                    binding.tvItemLetterTitle.text = "음료"
                    binding.tvItemLetterCount.text = "20"
                }
                2-> {
                    binding.ivItemLetterObject.setImageResource(R.drawable.img_deco_drink_2)
                    binding.tvItemLetterTitle.text = "???"
                    binding.tvItemLetterCount.text = "10"
                }
                3-> {
                    binding.ivItemLetterObject.setImageResource(R.drawable.img_photo_content)
                    binding.tvItemLetterTitle.text = "사진"
                    binding.tvItemLetterCount.text = "1"
                }
                else -> {
                    binding.ivItemLetterObject.setImageResource(R.drawable.img_deco_food_1)
                    binding.tvItemLetterTitle.text = "음식"
                    binding.tvItemLetterCount.text = "10"
                }
            }
        }

        fun initListener(clistener: Clistener, pos: Int) {
            binding.root.setOnClickListener {
                clistener.onClick(binding.tvItemLetterTitle.text.toString())
            }
        }
    }
}