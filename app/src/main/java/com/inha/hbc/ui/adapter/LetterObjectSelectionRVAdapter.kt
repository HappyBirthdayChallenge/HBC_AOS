package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.View
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
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterObjectSelectionRVAdapter: RecyclerView.Adapter<LetterObjectSelectionRVAdapter.ObjectHolder>() {

    interface MyListener {
        fun onClick(pos: Int)
    }
    lateinit var myListener: MyListener
    lateinit var binding: ItemLetterObjectBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectHolder {

        binding = ItemLetterObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ObjectHolder(binding, myListener)
    }

    override fun onBindViewHolder(holder: ObjectHolder, position: Int) {
        holder.bind(position)
        holder.initListener(position)
    }

    override fun getItemCount(): Int {
        return selectionCount(LetterFragmentManager.objectPageType)
    }
    class ObjectHolder(val binding: ItemLetterObjectBinding, val myListener: MyListener): RecyclerView.ViewHolder(binding.root) {
        fun bind(pos: Int) {
            binding.cvItemLetterObject.radius = 20F
            binding.tvItemLetterTitle.visibility = View.GONE
            binding.tvItemLetterCount.visibility = View.GONE
            var lp = binding.root.layoutParams
            (lp as FlexboxLayoutManager.LayoutParams).flexBasisPercent = "0.24".toFloat()
            lp.height = LetterFragmentManager.viewWidth / 3

            lp = binding.cvItemLetterObject.layoutParams as ViewGroup.MarginLayoutParams
            lp.setMargins(20, 20, 20, 10)
            binding.cvItemLetterObject.layoutParams = lp

            binding.ivItemLetterObject.setImageResource(selectionAssist(LetterFragmentManager.objectPageType, pos))
            binding.ivItemLetterObject.setPadding(20, 20, 20, 20)
        }


        fun initListener(pos: Int) {
            binding.root.setOnClickListener {
                myListener.onClick(pos)
            }
        }
    }

}