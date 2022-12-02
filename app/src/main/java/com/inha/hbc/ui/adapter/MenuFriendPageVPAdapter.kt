package com.inha.hbc.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.FollowerContent
import com.inha.hbc.data.remote.resp.menu.FollowerListSuccess
import com.inha.hbc.data.remote.resp.menu.FollowingContent
import com.inha.hbc.data.remote.resp.menu.FollowingListSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.ItemMenuFriendpageBinding
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.ui.menu.view.FollowerListView
import com.inha.hbc.ui.menu.view.FollowingListView
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService

class MenuFriendPageVPAdapter(val pos: Int): RecyclerView.Adapter<MenuFriendPageVPAdapter.FollowingPageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingPageHolder {
        return FollowingPageHolder(ItemMenuFriendpageBinding.inflate(LayoutInflater.from(parent.context), parent,false), pos)
    }

    override fun onBindViewHolder(holder: FollowingPageHolder, position: Int) {
        holder.init()
    }

    override fun getItemCount(): Int = 2

    class FollowingPageHolder(val binding: ItemMenuFriendpageBinding, val pos: Int): RecyclerView.ViewHolder(binding.root),
        FollowingListView, FollowerListView, RoomInfoView {
        var followingList =  ArrayList<FollowingContent?>()
        var followerList =  ArrayList<FollowerContent?>()
        var listSize = 0
        var page = 0
        var initV = true
        lateinit var selectedFollowingInfo: FollowingContent
        lateinit var selectedFollowerInfo: FollowerContent
        lateinit var followingAdapter: MenuFollowingListRVAdapter
        lateinit var followerAdapter: MenuFollowerListRVAdapter


        fun init() {
            if (pos == 0) {
                initFollowingRv()
                initFollowingView()
                initFollowingListener()
            }
            else {
                initFollowerRv()
                initFollowerView()
                initFollowerListener()
            }
        }

        fun initFollowerListener() {
            binding.rvMenuFriendlist.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastitem = (binding.rvMenuFriendlist.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
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
        fun initFollowingListener() {
            binding.rvMenuFriendlist.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastitem = (binding.rvMenuFriendlist.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemCount =  listSize - 1
                    if (lastitem == itemCount && !initV) {
                        Log.d("up", "overrid")
                        followingList.add(null)
                        listSize++
                        followingAdapter.notifyItemInserted(listSize - 1)
                        MenuRetrofitService().getFollowingList((page+1).toString(), 10.toString(), this@FollowingPageHolder)

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
        fun initFollowingRv() {
            followingAdapter = MenuFollowingListRVAdapter(followingList)
            followingAdapter.cstListener = object: MenuFollowingListRVAdapter.CstListener{
                override fun onClick(pos: Int, followingContent: FollowingContent) {
                    selectedFollowingInfo = followingContent
                    MessageRetrofitService().roomInfo(followingList[pos]!!.following.id.toString(), this@FollowingPageHolder)
                }

            }
            binding.rvMenuFriendlist.adapter= followingAdapter
        }
        fun initFollowerView() {
            Log.d("initv", "initv")
            followerList.add(null)
            listSize++
            followerAdapter.notifyItemInserted(listSize - 1)
            MenuRetrofitService().getFollowingList((page+1).toString(), 10.toString(), this)
        }

        fun initFollowingView() {
            Log.d("initv", "initv")
            followingList.add(null)
            listSize++
            followingAdapter.notifyItemInserted(listSize - 1)
            MenuRetrofitService().getFollowingList((page+1).toString(), 10.toString(), this)
        }


        override fun onFollowingListSuccess(resp: FollowingListSuccess) {
            followingList.removeAt(listSize - 1)
            followingAdapter.notifyItemRemoved(listSize - 1)
            listSize--
            for (i in resp.data.page.content) {
                followingList.add(i)
            }
            followingAdapter.notifyItemRangeInserted(listSize - 1, 10)
            listSize += 10
            initV = false
        }

        override fun onFollowingListFailure() {
            TODO("Not yet implemented")
        }



        override fun onRoomInfoSuccess(resp: RoomInfoSuccess) {
            if (pos == 0) {
                MenuFragmentManager.goPartyRoom(resp, selectedFollowingInfo)
            }
            else {
                MenuFragmentManager.goPartyRoom(resp, selectedFollowerInfo)
            }
        }

        override fun onRoomInfoFailure() {
            TODO("Not yet implemented")
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
        }

        override fun onFollowerListFailure() {
            TODO("Not yet implemented")
        }
    }
}