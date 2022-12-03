package com.inha.hbc.ui.menu.ui

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.*
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.ItemMyListVpBinding
import com.inha.hbc.ui.adapter.MenuFollowerListRVAdapter
import com.inha.hbc.ui.adapter.MenuFollowingListRVAdapter
import com.inha.hbc.ui.adapter.MenuMymessageRVAdapter
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.ui.menu.view.GetMymessageView
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService

class MymessageListVpHolder(val binding: ItemMyListVpBinding, data: GetProfileSuccess):RecyclerView.ViewHolder(binding.root), GetMymessageView, RoomInfoView {
    lateinit var selectedInfo: Content
    lateinit var adapter: MenuMymessageRVAdapter
    var dataArr =  ArrayList<Content?>()
    var listSize = 0
    var page = 0
    var initV = true
    var pos = 0

    fun init() {
        this.pos = pos
        initRv()
        initView()
        initListener()
        }

        fun initListener() {
            binding.rvItemMyListVp.addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lastitem = (binding.rvItemMyListVp.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    val itemCount =  listSize - 1
                    if (lastitem == itemCount && !initV) {
                        dataArr.add(null)
                        listSize++
                        adapter.notifyItemInserted(listSize - 1)
                        MenuRetrofitService().getMymessage((page+1).toString(), 10.toString(), this@MymessageListVpHolder)

                    }
                }
            })
        }


        fun initRv() {
            adapter = MenuMymessageRVAdapter(dataArr)
            adapter.setMymessage = object: MenuMymessageRVAdapter.SetMymessage{
                override fun onClick(pos: Int) {
                    selectedInfo = dataArr[pos]!!
                    MessageRetrofitService().roomInfo(dataArr[pos]!!.room_owner.id.toString(), this@MymessageListVpHolder)
                }

            }
            binding.rvItemMyListVp.adapter= adapter
        }


        fun initView() {
            dataArr.add(null)
            listSize++
            adapter.notifyItemInserted(listSize - 1)
            MenuRetrofitService().getMymessage((page+1).toString(), 10.toString(), this)
        }

        override fun onGetMymessageSuccess(resp: GetMymessageSuccess) {
            dataArr.removeAt(listSize - 1)
            adapter.notifyItemRemoved(listSize - 1)
            listSize--
            for (i in resp.data.page.content) {
                dataArr.add(i)
            }
            adapter.notifyItemRangeInserted(listSize - 1, 10)
            listSize += 10
            initV = false
        }

    override fun onGetMymessageFailure() {
        TODO("Not yet implemented")
    }

    override fun onRoomInfoSuccess(resp: RoomInfoSuccess) {
        MenuFragmentManager.goPartyRoom(resp, selectedInfo)
    }

    override fun onRoomInfoFailure() {
        TODO("Not yet implemented")
    }

}