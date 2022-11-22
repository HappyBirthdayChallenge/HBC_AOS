package com.inha.hbc.ui.menu.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.data.remote.resp.menu.FriendlistSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.FragmentMenuFriendlistBinding
import com.inha.hbc.ui.adapter.MenuFriendListRVAdapter
import com.inha.hbc.ui.adapter.MenuFriendPageVPAdapter
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.ui.menu.view.FriendListView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.menu.MenuRetroService
import com.inha.hbc.util.network.message.MessageRetrofitService

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