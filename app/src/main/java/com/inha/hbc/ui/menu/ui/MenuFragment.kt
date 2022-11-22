package com.inha.hbc.ui.menu.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentMenuBinding
import com.inha.hbc.ui.adapter.MenuListRVAdapter
import com.inha.hbc.ui.login.ui.LoginActivity
import com.inha.hbc.ui.menu.view.SignoutView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication

class MenuFragment(): Fragment(), SignoutView {

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
        initListener()
        initAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }

    fun initListener() {
        binding.ivMenuClose.setOnClickListener {
            MenuFragmentManager.close()
        }

        binding.tvMenuSignout.setOnClickListener {
            RetrofitService().signout(GlobalApplication.prefs.getFcmtoken()!!, this)
        }


    }

    fun initAdapter() {
        var menuList = ArrayList<String>()
        menuList.add("친구목록")
        menuList.add("알림함")
        rvAdapter = MenuListRVAdapter(menuList)
        rvAdapter.onlistener = object: MenuListRVAdapter.onListener{
            override fun onClick(menu: String) {
                when (menu) {
                    "친구목록" -> {
                        MainFragmentManager.manager.beginTransaction().add(MainFragmentManager.id, FriendListFragment()).commit()
                    }
                    else -> {

                    }
                }
            }

        }
        binding.rvMenu.adapter = rvAdapter
    }

    override fun onSignoutSuccess() {
        GlobalApplication.prefs.delJwt()
        GlobalApplication.prefs.delFcmtoken()

        val intent = Intent(MainFragmentManager.baseActivity, LoginActivity::class.java)
        startActivity(intent)
        MainFragmentManager.baseActivity.finish()

    }

    override fun onSignoutFailure() {
        Toast.makeText(requireContext(), "에러로 로그아웃 실패!",Toast.LENGTH_SHORT).show()
    }
}
