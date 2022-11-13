package com.inha.hbc.ui.letter

import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.databinding.FragmentObjectBinding
import com.inha.hbc.ui.adapter.LetterObjectRVAdapter
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class ObjectFragment(val binding: FragmentObjectBinding): RecyclerView.ViewHolder(binding.root) {


    fun bind(pos: Int) {
        initRv(pos)
    }

    fun initRv(pos: Int) {
        if (pos == 1) {

        }
        else {

        }
        val objectAdapter = LetterObjectRVAdapter(pos)
        objectAdapter.clistener = object: LetterObjectRVAdapter.Clistener {
            override fun onClick(title: String) {
                MainFragmentManager.letterData.objectId = title
                MainFragmentManager.objectOpen(title)
            }

        }
        binding.rvObject.adapter = objectAdapter
        val objectLayoutManager = FlexboxLayoutManager(MainFragmentManager.letterBaseFragment.context)
        objectLayoutManager.alignItems = AlignItems.FLEX_START
        binding.rvObject.layoutManager = objectLayoutManager
    }
}