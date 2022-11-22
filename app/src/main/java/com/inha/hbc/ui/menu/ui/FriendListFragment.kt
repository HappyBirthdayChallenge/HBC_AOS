package com.inha.hbc.ui.menu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMenuFriendlistBinding
import com.inha.hbc.ui.adapter.MenuFriendPageVPAdapter

class FriendListFragment: Fragment() {
    lateinit var binding: FragmentMenuFriendlistBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuFriendlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initView()
    }

   fun initListener() {
       binding.ivMenuFriendlistBack.setOnClickListener {
           parentFragmentManager.beginTransaction().remove(this).commit()
       }
   }

    fun initView() {
        val adapter = MenuFriendPageVPAdapter()
        binding.vpMenuFriendlist.adapter = adapter
    }

}