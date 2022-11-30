package com.inha.hbc.ui.letter.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.inha.hbc.databinding.FragmentObjectSelectionBinding
import com.inha.hbc.ui.adapter.LetterObjectSelectionRVAdapter
import com.inha.hbc.ui.assist.selectionAssist
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager

class ObjectSelectionFragment: Fragment() {
    lateinit var binding: FragmentObjectSelectionBinding
    lateinit var callback: OnBackPressedCallback
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

        initListener()
        initView()

    }

    private fun initRv() {
        val adapter = LetterObjectSelectionRVAdapter()
        adapter.setHasStableIds(true)
        adapter.myListener = object : LetterObjectSelectionRVAdapter.MyListener {
            override fun onClick(pos: Int) {
                LetterFragmentManager.letterData.objectName += pos.toString()
                LetterFragmentManager.objectId = selectionAssist(LetterFragmentManager.objectPageType, pos)
                LetterFragmentManager.objectClose(this@ObjectSelectionFragment, true)
            }
        }
        binding.rvObjectSelection.adapter = adapter

        val selectLayoutManager = FlexboxLayoutManager(context)
        selectLayoutManager.alignItems = AlignItems.FLEX_START
        binding.rvObjectSelection.layoutManager = selectLayoutManager
    }

    fun initView() {

    }

    fun initListener() {
        binding.ivObjectSelectionBack.setOnClickListener {
            LetterFragmentManager.objectClose(this@ObjectSelectionFragment, false)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                LetterFragmentManager.objectClose(this@ObjectSelectionFragment, false)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}