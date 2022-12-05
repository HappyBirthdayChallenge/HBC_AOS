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
import com.inha.hbc.data.remote.resp.menu.Content
import com.inha.hbc.data.remote.resp.menu.GetMymessageSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.data.remote.resp.room.GetReceiveMessageSuccess
import com.inha.hbc.data.remote.resp.room.ReceiveMessageContent
import com.inha.hbc.databinding.FragmentMainMymessageBinding
import com.inha.hbc.ui.adapter.MainMymessageRVAdapter
import com.inha.hbc.ui.adapter.MenuMymessageRVAdapter
import com.inha.hbc.ui.main.view.GetReceiveMessageView
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.network.room.RoomRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MainMymessageFragment: Fragment(), GetReceiveMessageView {
    lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentMainMymessageBinding
    lateinit var adapter: MainMymessageRVAdapter
    lateinit var selectedInfo: ReceiveMessageContent
    var dataArr =  ArrayList<ReceiveMessageContent?>()
    var listSize = 0
    var page = 0
    var initV = true

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
        initListener()
        initView()
    }

    fun initListener() {
        binding.rvMainMymessage.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastitem = (binding.rvMainMymessage.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val itemCount =  listSize - 1
                if (lastitem == itemCount && !initV) {
                    dataArr.add(null)
                    listSize++
                    adapter.notifyItemInserted(listSize - 1)
                    RoomRetrofitService().getReceiveMessage((page+1).toString(),MainFragmentManager.roomId.toString(), 10.toString(), this@MainMymessageFragment)

                }
            }
        })

        binding.ivMainMymessageBack.setOnClickListener {
            MainFragmentManager.closeMessageList(this)
        }
    }


    fun initRv() {
        adapter = MainMymessageRVAdapter(dataArr)
        adapter.setReceiveMessage = object: MainMymessageRVAdapter.SetReceiveMessage{
            override fun onClick(pos: Int) {
                selectedInfo = dataArr[pos]!!
                MainFragmentManager.closeMessageList(this@MainMymessageFragment)
            }
        }
        binding.rvMainMymessage.adapter= adapter
    }


    fun initView() {
        dataArr.add(null)
        listSize++
        adapter.notifyItemInserted(listSize - 1)
        RoomRetrofitService().getReceiveMessage((page+1).toString(),MainFragmentManager.roomId.toString(), 10.toString(), this@MainMymessageFragment)
    }
    override fun onGetReceiveMessageSuccess(resp: GetReceiveMessageSuccess) {
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

    override fun onGetReceiveMessageFailure() {
        TODO("Not yet implemented")
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MainFragmentManager.closeMessageList(this@MainMymessageFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}