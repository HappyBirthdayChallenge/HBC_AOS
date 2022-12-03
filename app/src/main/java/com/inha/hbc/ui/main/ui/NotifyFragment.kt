package com.inha.hbc.ui.main.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentNotifyBinding
import com.inha.hbc.ui.adapter.MainNotifyRVAdapter
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class NotifyFragment: Fragment() {
    lateinit var callback: OnBackPressedCallback
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
        initListener()
    }

    fun initRv() {
        adapter = MainNotifyRVAdapter()
        binding.rvNotify.adapter = adapter
    }

    fun initListener() {
        binding.ivNotifyBack.setOnClickListener {
            MainFragmentManager.closeNotify(this)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MainFragmentManager.closeNotify(this@NotifyFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}