package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.data.remote.resp.menu.GetMymessageSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.FragmentMainMymessageBinding
import com.inha.hbc.ui.adapter.MainMymessageRVAdapter
import com.inha.hbc.ui.adapter.MenuMymessageRVAdapter
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService

class MainMymessageFragment: Fragment() {
    lateinit var binding: FragmentMainMymessageBinding
    lateinit var adapter: MainMymessageRVAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMymessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRv()
    }lateinit var selectedInfo: Content
    lateinit var adapter: MenuMymessageRVAdapter
    var dataArr =  ArrayList<Content?>()
    var listSize = 0
    var page = 0
    var initV = true

    fun init() {
        initRv()
        initListener()
        initView()
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

        val manager = LinearLayoutManager(MainFragmentManager.baseActivity.applicationContext)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.rvItemMyListVp.layoutManager = manager
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

    fun initRv() {
        adapter = MainMymessageRVAdapter()

    }
}