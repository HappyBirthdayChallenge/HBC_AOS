package com.inha.hbc.ui.menu.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMypageBinding
import com.inha.hbc.ui.adapter.MypageRVAdapter
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager

class MypageFragment: Fragment() {
    lateinit var binding: FragmentMypageBinding
    lateinit var adapter: MypageRVAdapter
    lateinit var backPressedCallback: OnBackPressedCallback
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        initListener()
    }

    fun initRv() {
        adapter = MypageRVAdapter()
        binding.rvMy.adapter = adapter
    }

    fun initListener() {
        binding.ivMyBack.setOnClickListener {
            MenuFragmentManager.close()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MenuFragmentManager.close()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }
}