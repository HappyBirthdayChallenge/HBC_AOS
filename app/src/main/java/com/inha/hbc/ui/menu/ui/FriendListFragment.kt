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
import com.inha.hbc.databinding.FragmentMenuFriendlistBinding
import com.inha.hbc.ui.adapter.MenuFriendListRVAdapter
import com.inha.hbc.ui.menu.view.FriendListView
import com.inha.hbc.util.network.menu.MenuRetroService

class FriendListFragment: Fragment(), FriendListView {
    lateinit var binding: FragmentMenuFriendlistBinding
    var friendList =  ArrayList<Content?>()
    var listSize = 0
    var page = 0
    var initV = true

    lateinit var adapter: MenuFriendListRVAdapter
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

        initRv()
        initView()
        initListener()
    }

    fun initListener() {
        binding.ivMenuFriendlistBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
        binding.rvMenuFriendlist.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastitem = (binding.rvMenuFriendlist.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val itemCount =  listSize - 1
                if (lastitem == itemCount && !initV) {
                    Log.d("up", "overrid")
                    friendList.add(null)
                    listSize++
                    adapter.notifyItemInserted(listSize - 1)
                    MenuRetroService().friendList((page+1).toString(), 10.toString(), this@FriendListFragment)

                }
            }
        })
    }

    fun initRv() {
        adapter = MenuFriendListRVAdapter(friendList)
        binding.rvMenuFriendlist.adapter= adapter
    }

    fun initView() {
        Log.d("initv", "initv")
        friendList.add(null)
        listSize++
        adapter.notifyItemInserted(listSize - 1)
        MenuRetroService().friendList((page+1).toString(), 10.toString(), this)
    }

    override fun onFriendListSuccess(resp: FriendlistSuccess) {
        friendList.removeAt(listSize - 1)
        adapter.notifyItemRemoved(listSize - 1)
        listSize--
        for (i in resp.data.page.content) {
            friendList.add(i)
        }
        adapter.notifyItemRangeInserted(listSize - 1, 10)
        listSize += 10
        initV = false
    }

    override fun onFriendListFailure() {
        TODO("Not yet implemented")
    }
}