package com.inha.hbc.ui.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMenuBinding
import com.inha.hbc.ui.adapter.MenuListRVAdapter

class MenuFragment(val flid: Int): Fragment(), MenuListRVAdapter.onListener {
    interface OnCloseDetection {
        fun onClose()
    }
    lateinit var onclose: OnCloseDetection
    lateinit var binding: FragmentMenuBinding
    lateinit var rvAdapter: MenuListRVAdapter
    lateinit var mainContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var menuList = ArrayList<String>()
        menuList.add("친구목록")
        menuList.add("알림함")
        rvAdapter = MenuListRVAdapter(menuList)
        rvAdapter.setListener(this)
        binding.rvMenu.adapter = rvAdapter

        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }
    override fun onDestroyView() {
        super.onDestroyView()
        onclose.onClose()
    }

    fun initListener() {
        binding.ivMenuClose.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }


    }

    fun setClose(data: OnCloseDetection) {
        onclose = data
    }

    override fun onClick(menu: String) {//recyclerview 클릭리스너
        var menuString =
            when(menu) {
                "친구목록" -> {
                    "friendlist"
                }
                "알림함" -> {
                    "notify"
                }
                else -> {
                    ""
                }
            }
        val downFragment: Fragment? =
            when(menu) {
                "친구목록" -> {
                    FriendListFragment()
                }
                "알림함" -> {
                    NotifyFragment()
                }
                else -> {
                    null
                }
            }
        parentFragmentManager.beginTransaction().addToBackStack(menuString).add(flid, downFragment!!).commit()
        }
    }
