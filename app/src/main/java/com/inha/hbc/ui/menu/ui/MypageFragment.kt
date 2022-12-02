package com.inha.hbc.ui.menu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMypageBinding
import com.inha.hbc.ui.adapter.MypageRVAdapter

class MypageFragment: Fragment() {
    lateinit var binding: FragmentMypageBinding
    lateinit var adapter: MypageRVAdapter
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
    }

    fun initRv() {
        adapter = MypageRVAdapter()
        binding.rvMy.adapter = adapter
    }
}