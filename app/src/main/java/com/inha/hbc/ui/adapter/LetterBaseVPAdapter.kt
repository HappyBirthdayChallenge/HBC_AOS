package com.inha.hbc.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.databinding.FragmentLetterBinding
import com.inha.hbc.databinding.FragmentObjectBinding
import com.inha.hbc.ui.letter.LetterFragment
import com.inha.hbc.ui.letter.ObjectFragment

class LetterBaseVPAdapter(val typeData: ArrayList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var objectBinding: FragmentObjectBinding
    lateinit var letterBinding: FragmentLetterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            objectBinding = FragmentObjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ObjectFragment(objectBinding)
        } else {
            letterBinding = FragmentLetterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LetterFragment(letterBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 2) {
            (holder as LetterFragment).bind()
        }
        else {
            (holder as ObjectFragment).bind(position)
        }
    }

    override fun getItemCount(): Int  = 3

    override fun getItemViewType(position: Int): Int {
        return typeData[position]
    }
}