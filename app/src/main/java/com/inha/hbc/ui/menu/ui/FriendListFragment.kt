package com.inha.hbc.ui.menu.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLayoutChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.inha.hbc.databinding.FragmentMenuFriendlistBinding
import com.inha.hbc.ui.adapter.MenuFriendPageVPAdapter
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager

class FriendListFragment(val firstPos: Int): Fragment() {
    lateinit var binding: FragmentMenuFriendlistBinding
    lateinit var backPressedCallback: OnBackPressedCallback
    val adapter = MenuFriendPageVPAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuFriendlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initView()
    }

   fun initListener() {
       binding.ivMenuFriendlistBack.setOnClickListener {
           MenuFragmentManager.closeFriendList(this)
       }
       binding.vpMenuFriendlist.registerOnPageChangeCallback(object : OnPageChangeCallback() {
           override fun onPageScrolled(
               position: Int,
               positionOffset: Float,
               positionOffsetPixels: Int
           ) {
               super.onPageScrolled(position, positionOffset, positionOffsetPixels)
               val imm  = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
               imm.hideSoftInputFromWindow(this@FriendListFragment.binding.root.windowToken, 0)
           }
       })
   }


    fun initView() {
        adapter.setNum = object :MenuFriendPageVPAdapter.SetFriendPageVp {
            override fun setNum(pos: Int, num: Int) {
                binding.tlMenuFriendlist.getTabAt(pos)!!.text = if (pos == 0) {
                    "팔로잉 ${num}명"
                }
                else {
                    "팔로워 ${num}명"
                }
            }

            override fun setCurrentPage() {
                binding.vpMenuFriendlist.currentItem = firstPos
            }

        }
        binding.vpMenuFriendlist.adapter = adapter
        binding.vpMenuFriendlist.offscreenPageLimit = 1


        val titles = listOf("팔로잉", "팔로워")
        TabLayoutMediator(binding.tlMenuFriendlist, binding.vpMenuFriendlist
        ) { tab, position -> tab.text = titles[position] }.attach()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                MenuFragmentManager.closeFriendList(this@FriendListFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }
}