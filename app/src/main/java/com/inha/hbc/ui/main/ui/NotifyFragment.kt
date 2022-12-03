package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentNotifyBinding
import com.inha.hbc.ui.adapter.MainNotifyRVAdapter

class NotifyFragment: Fragment() {
    lateinit var binding: FragmentNotifyBinding
    lateinit var adapter: MainNotifyRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  FragmentNotifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    fun initRv() {
        adapter = MainNotifyRVAdapter()
        binding.rvNotify.adapter = adapter
    }
}