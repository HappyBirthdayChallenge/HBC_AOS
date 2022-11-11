package com.inha.hbc.ui.menu.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMenuBinding
import com.inha.hbc.ui.adapter.MenuListRVAdapter
import com.inha.hbc.ui.login.ui.LoginActivity
import com.inha.hbc.ui.menu.view.SignoutView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MenuFragment(): Fragment(), MenuListRVAdapter.onListener, SignoutView {

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

    fun initListener() {
        binding.ivMenuClose.setOnClickListener {
            MainFragmentManager.menuClose(this)
        }

        binding.tvMenuSignout.setOnClickListener {
            RetrofitService().signout(this)
        }


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
//        val downFragment: Fragment? =
//            when(menu) {
//                "친구목록" -> {
//                    FriendListFragment()
//                }
//                "알림함" -> {
//                    NotifyFragment()
//                }
//                else -> {
//                    null
//                }
//            }
//        parentFragmentManager.beginTransaction().addToBackStack(menuString).add(flid, downFragment!!).commit()
        }

    override fun onSignoutSuccess() {
        GlobalApplication.prefs.delJwt()

        val intent = Intent(MainFragmentManager.baseActivity, LoginActivity::class.java)
        startActivity(intent)
        MainFragmentManager.baseActivity.finish()

    }

    override fun onSignoutFailure() {
        TODO("Not yet implemented")
    }
}
