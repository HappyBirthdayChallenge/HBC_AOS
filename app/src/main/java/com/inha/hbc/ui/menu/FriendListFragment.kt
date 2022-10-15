package com.inha.hbc.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentFriendlistBinding

class FriendListFragment: Fragment() {
    lateinit var binding: FragmentFriendlistBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.ivMenuFriendlistBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }
}