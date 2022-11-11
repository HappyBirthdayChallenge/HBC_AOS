package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayout.LayoutParams
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.databinding.ItemLetterObjectBinding
import com.inha.hbc.ui.assist.selectionAssist
import com.inha.hbc.ui.assist.selectionCount
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterObjectSelectionRVAdapter: RecyclerView.Adapter<LetterObjectSelectionRVAdapter.ObjectHolder>() {
    lateinit var binding: ItemLetterObjectBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectHolder {

        binding = ItemLetterObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObjectHolder(binding)
    }

    override fun onBindViewHolder(holder: ObjectHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return selectionCount(MainFragmentManager.objectPageType)
    }
    class ObjectHolder(val binding: ItemLetterObjectBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.cvItemLetterObject.radius = 20F
            var lp = binding.root.layoutParams
            (lp as FlexboxLayoutManager.LayoutParams).flexBasisPercent = "0.19".toFloat()
            lp.height = MainFragmentManager.viewWidth / 3

            lp = binding.cvItemLetterObject.layoutParams as ViewGroup.MarginLayoutParams
            lp.updateMargins(5, 5, 5, 5)

            lp = binding.ivItemLetterObject.layoutParams
            binding.ivItemLetterObject.setPadding(5, 5, 5, 5)




            binding.ivItemLetterObject.setImageResource(selectionAssist(MainFragmentManager.objectPageType, pos))
        }
    }

}