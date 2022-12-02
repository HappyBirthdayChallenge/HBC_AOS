package com.inha.hbc.ui.menu.ui

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.FollowerContent
import com.inha.hbc.data.remote.resp.menu.FollowingContent
import com.inha.hbc.databinding.ItemMyListVpBinding
import com.inha.hbc.ui.adapter.MenuFollowingListRVAdapter
import com.inha.hbc.ui.adapter.MenuPartyroomRVAdapter
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService

class MypageListVpHolder(val binding: ItemMyListVpBinding): RecyclerView.ViewHolder(binding.root) {
    lateinit var adapter: MenuPartyroomRVAdapter
    var roomList =  ArrayList<FollowerContent?>()
    var listSize = 0
    var page = 0
    var initV = true

    fun init(pos: Int) {
        initRv(pos)
        initPartyroomListener()
    }
    fun initRv(pos: Int) {
        adapter = MenuPartyroomRVAdapter(followerList)
        binding.rvItemMyListVp.adapter = adapter
    }  f
    un initFollowingRv() {
        followingAdapter = MenuFollowingListRVAdapter(followingList)
        followingAdapter.cstListener = object: MenuFollowingListRVAdapter.CstListener{
            override fun onClick(pos: Int, followingContent: FollowingContent) {
                selectedFollowingInfo = followingContent
                MessageRetrofitService().roomInfo(followingList[pos]!!.following.id.toString(), this@FollowingPageHolder)
            }

        }
        binding.rvMenuFriendlist.adapter= followingAdapter
    }

    fun initPartyroomListener() {
        binding.rvItemMyListVp.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastitem = (binding.rvItemMyListVp.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val itemCount =  listSize - 1
                if (lastitem == itemCount && !initV) {
                    followerList.add(null)
                    listSize++
                    followerAdapter.notifyItemInserted(listSize - 1)
                    MenuRetrofitService().getFollowerList((page+1).toString(), 10.toString(), this@MypageListVpHolder)

                }
            }
        })
    }
}