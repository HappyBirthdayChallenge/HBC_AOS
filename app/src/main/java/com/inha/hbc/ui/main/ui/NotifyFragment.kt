package com.inha.hbc.ui.main.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.room.ReceiveMessageContent
import com.inha.hbc.databinding.FragmentNotifyBinding
import com.inha.hbc.ui.adapter.MainNotifyRVAdapter
import com.inha.hbc.ui.main.view.GetNotifySuccess
import com.inha.hbc.ui.main.view.GetNotifyView
import com.inha.hbc.ui.main.view.NotifyContent
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.room.RoomRetrofitService

class NotifyFragment: Fragment(), GetNotifyView {
    lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentNotifyBinding
    lateinit var adapter: MainNotifyRVAdapter
    lateinit var selectedInfo: NotifyContent
    var dataArr =  ArrayList<NotifyContent?>()
    var listSize = 0
    var page = 0
    var initV = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=  FragmentNotifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        initView()
        initListener()
    }

    fun initRv() {
        adapter = MainNotifyRVAdapter(dataArr)
        adapter.setNotify = object :MainNotifyRVAdapter.SetNotify{
            override fun onClick(pos: Int) {
            }
        }
        binding.rvNotify.adapter = adapter
    }

    fun initListener() {
        binding.ivNotifyBack.setOnClickListener {
            MainFragmentManager.closeNotify(this)
        }
        binding.rvNotify.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastitem = (binding.rvNotify.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val itemCount =  listSize - 1
                if (lastitem == itemCount && !initV) {
                    dataArr.add(null)
                    listSize++
                    adapter.notifyItemInserted(listSize - 1)
                    RoomRetrofitService().getNotify((page+1).toString(), 10.toString(), this@NotifyFragment)

                }
            }
        })
    }

    fun initView() {
        dataArr.add(null)
        listSize++
        adapter.notifyItemInserted(listSize - 1)
        RoomRetrofitService().getNotify((page+1).toString(), 10.toString(), this@NotifyFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MainFragmentManager.closeNotify(this@NotifyFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onGetNotifySuccess(resp: GetNotifySuccess) {
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

    override fun onGetNotifyFailure() {
        TODO("Not yet implemented")
    }
}