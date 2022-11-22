package com.inha.hbc.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.data.remote.resp.menu.FriendlistSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.ItemMenuFriendpageBinding
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.ui.menu.view.FriendListView
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService

class MenuFriendPageVPAdapter: RecyclerView.Adapter<MenuFriendPageVPAdapter.FriendPageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendPageHolder {
        return FriendPageHolder(ItemMenuFriendpageBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: FriendPageHolder, position: Int) {
        holder.init()
    }

    override fun getItemCount(): Int = 2

    class FriendPageHolder(val binding: ItemMenuFriendpageBinding): RecyclerView.ViewHolder(binding.root),
        FriendListView, RoomInfoView {
        var friendList =  ArrayList<Content?>()
        var listSize = 0
        var page = 0
        var initV = true
        lateinit var adapter: MenuFriendListRVAdapter


        fun init() {
            initRv()
            initView()
            initListener()
        }
        fun initListener() {
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
                        MenuRetrofitService().friendList((page+1).toString(), 10.toString(), this@FriendPageHolder)

                    }
                }
            })
        }

        fun initRv() {
            adapter = MenuFriendListRVAdapter(friendList)
            adapter.cstListener = object: MenuFriendListRVAdapter.CstListener{
                override fun onClick(pos: Int) {
                    MessageRetrofitService().roomInfo(friendList[pos]!!.member.id.toString(), this@FriendPageHolder)
                }

            }
            binding.rvMenuFriendlist.adapter= adapter
        }

        fun initView() {
            Log.d("initv", "initv")
            friendList.add(null)
            listSize++
            adapter.notifyItemInserted(listSize - 1)
            MenuRetrofitService().friendList((page+1).toString(), 10.toString(), this)
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



        override fun onRoomInfoSuccess(resp: RoomInfoSuccess) {
            MenuFragmentManager.goPartyRoom(resp)
        }

        override fun onRoomInfoFailure() {
            TODO("Not yet implemented")
        }
    }
}