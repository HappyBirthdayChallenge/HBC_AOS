package com.inha.hbc.ui.letter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.databinding.FragmentObjectSelectionBinding
import com.inha.hbc.ui.adapter.LetterObjectSelectionRVAdapter
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class ObjectSelectionFragment: Fragment() {
    lateinit var binding: FragmentObjectSelectionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentObjectSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRv()

        initView()

    }

    private fun initRv() {
        if (MainFragmentManager.objectPageType == 3) {
            binding.tvObjectSelectionPic.visibility = View.VISIBLE
            binding.rvObjectSelection.visibility = View.GONE
            return
        }
        val adapter = LetterObjectSelectionRVAdapter()
        adapter.myListener = object : LetterObjectSelectionRVAdapter.MyListener {
            override fun onClick(pos: Int) {
                MainFragmentManager.letterData.objectId += pos.toString()
            }
        }
        binding.rvObjectSelection.adapter = adapter

        val selectLayoutManager = FlexboxLayoutManager(context)
        selectLayoutManager.alignItems = AlignItems.FLEX_START
        binding.rvObjectSelection.layoutManager = selectLayoutManager
    }

    fun initView() {

    }

}