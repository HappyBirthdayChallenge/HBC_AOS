package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.databinding.ItemLetterObjectBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterAnimeRVAdapter:RecyclerView.Adapter<LetterAnimeRVAdapter.AnimeHolder>() {
    interface MyListener {
        fun onClick(title: String)
    }
    lateinit var myListener: MyListener
    lateinit var binding: ItemLetterObjectBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        binding = ItemLetterObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeHolder(binding, myListener)
    }

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int  = 9

    class AnimeHolder(val binding: ItemLetterObjectBinding, val myListener: MyListener): RecyclerView.ViewHolder(binding.root) {
        fun init(pos: Int) {
            binding.cvItemLetterAnime.visibility = View.VISIBLE

            binding.lavItemLetterObject.setAnimation("json_deco_anime_${pos+1}.json")


            binding.cvItemLetterAnime.radius = 20F
            var lp = binding.root.layoutParams
            (lp as FlexboxLayoutManager.LayoutParams).flexBasisPercent = "0.5".toFloat()
            lp.height = MainFragmentManager.viewWidth/2
            binding.root.layoutParams = lp

            initListener(pos)
        }

        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                myListener.onClick("json_deco_anime_$pos")
            }
        }

    }
}