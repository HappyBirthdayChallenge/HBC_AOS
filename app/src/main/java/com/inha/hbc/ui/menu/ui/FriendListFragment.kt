package com.inha.hbc.ui.menu.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.inha.hbc.databinding.FragmentMenuFriendlistBinding
import com.inha.hbc.ui.adapter.MenuFriendPageVPAdapter
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager

class FriendListFragment(val firstPos: Int): Fragment() {
    lateinit var binding: FragmentMenuFriendlistBinding
    lateinit var backPressedCallback: OnBackPressedCallback
    val adapter = MenuFriendPageVPAdapter()

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
           MenuFragmentManager.closeFriendList(this)
       }
   }

    fun initView() {
        binding.vpMenuFriendlist.adapter = adapter


        val titles = listOf("팔로잉", "팔로워")
        TabLayoutMediator(binding.tlMenuFriendlist, binding.vpMenuFriendlist
        ) { tab, position -> tab.text = titles[position] }.attach()

        binding.vpMenuFriendlist.currentItem = firstPos
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MenuFragmentManager.closeFriendList(this@FriendListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }
}