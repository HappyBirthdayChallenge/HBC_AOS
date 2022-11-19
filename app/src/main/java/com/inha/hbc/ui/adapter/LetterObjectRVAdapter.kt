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
        return ObjectHolder(binding, clistener)
    }

    override fun onBindViewHolder(holder: ObjectHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 5

    class ObjectHolder(val binding: ItemLetterObjectBinding, val clistener: Clistener): RecyclerView.ViewHolder(binding.root) {
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
                    binding.ivItemLetterObject.setImageResource(R.drawable.img_deco_toy_1)
                    binding.tvItemLetterTitle.text = "장난감"
                    binding.tvItemLetterCount.text = "9"
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

            initListener(pos)
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                clistener.onClick(
                    when (pos) {
                        0 -> "img_deco_gift_"
                        1 -> "img_deco_drink_"
                        2 -> "img_deco_toy_"
                        3 -> "img_pic_"
                        else -> "img_deco_food_"
                    }
                )
            }
        }
    }
}