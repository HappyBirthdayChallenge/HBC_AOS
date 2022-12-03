package com.inha.hbc.ui.menu.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.FollowerContent
import com.inha.hbc.data.remote.resp.menu.FollowerListSuccess
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMyListVpBinding
import com.inha.hbc.ui.adapter.MenuFollowerListRVAdapter
import com.inha.hbc.ui.adapter.MenuFollowingListRVAdapter
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService

class MymessageListVpHolder(val binding: ItemMyListVpBinding, data: GetProfileSuccess):RecyclerView.ViewHolder(binding.root) {
    var followerList =  ArrayList<FollowerContent?>()
    var listSize = 0
    var page = 0
    var initV = true
    var pos = 0
    fun init() {
        initRv()
    }

    fun initRv() {
        lateinit var selectedFollowerInfo: FollowerContent
        lateinit var followingAdapter: MenuFollowingListRVAdapter
    }


    fun init(pos: Int) {
        this.pos = pos
        if (pos == 0) {
            initFollowingRv()
            initFollowingView()
            initFollowingListener()
        }

        fun initFollowerListener() {
            binding.rvItemMyListVp.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastitem = (binding.rvItemMyListVp.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemCount =  listSize - 1
                    if (lastitem == itemCount && !initV) {
                        followerList.add(null)
                        listSize++
                        followerAdapter.notifyItemInserted(listSize - 1)
                        MenuRetrofitService().getFollowerList((page+1).toString(), 10.toString(), this@FollowingPageHolder)

                    }
                }
            })
        }


        fun initFollowerRv() {
            followerAdapter = MenuFollowerListRVAdapter(followerList)
            followerAdapter.cstListener = object: MenuFollowerListRVAdapter.CstListener{
                override fun onClick(pos: Int, followerContent: FollowerContent) {
                    selectedFollowerInfo = followerContent
                    MessageRetrofitService().roomInfo(followerList[pos]!!.follower.id.toString(), this@FollowingPageHolder)
                }

            }
            binding.rvMenuFriendlist.adapter= followerAdapter
        }


        fun initFollowerView() {
            Log.d("initv", "initv")
            followerList.add(null)
            listSize++
            followerAdapter.notifyItemInserted(listSize - 1)
            MenuRetrofitService().getFollowerList((page+1).toString(), 10.toString(), this)
        }
            override fun onFollowerListSuccess(resp: FollowerListSuccess) {
            followerList.removeAt(listSize - 1)
            followerAdapter.notifyItemRemoved(listSize - 1)
            listSize--
            for (i in resp.data.page.content) {
                followerList.add(i)
            }
            followerAdapter.notifyItemRangeInserted(listSize - 1, 10)
            listSize += 10
            initV = false
            setFriendPageVp.setNum(1, resp.data.page.totalElements)
        }

}