package com.inha.hbc.ui.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inha.hbc.data.remote.resp.main.GlobalSearchSuccess
import com.inha.hbc.data.remote.resp.menu.*
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.ItemMenuFriendpageBinding
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.ui.menu.view.FollowerListView
import com.inha.hbc.ui.menu.view.FollowingListView
import com.inha.hbc.ui.menu.view.SearchFollowView
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.main.MainRetrofitService
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService
import java.util.regex.Pattern
import kotlin.concurrent.timer

class MenuFriendPageVPAdapter(): RecyclerView.Adapter<MenuFriendPageVPAdapter.FollowingPageHolder>() {

    interface SetFriendPageVp {
        fun setNum(pos: Int, num: Int)
        fun setCurrentPage()
    }

    lateinit var setNum: SetFriendPageVp
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingPageHolder {
        return FollowingPageHolder(ItemMenuFriendpageBinding.inflate(LayoutInflater.from(parent.context), parent,false), setNum)
    }

    override fun onBindViewHolder(holder: FollowingPageHolder, position: Int) {
        holder.init(position)
    }

    override fun getItemCount(): Int = 2

    class FollowingPageHolder(val binding: ItemMenuFriendpageBinding, val setFriendPageVp: SetFriendPageVp): RecyclerView.ViewHolder(binding.root),
        FollowingListView, FollowerListView, RoomInfoView, SearchFollowView {
        var followingList =  ArrayList<FollowingContent?>()
        var followerList =  ArrayList<FollowerContent?>()
        var listSize = 0
        var page = 0
        var initV = true
        var pos = 0

        var isTyping = false
        var time = 0
        lateinit var selectedFollowingInfo: FollowingContent
        lateinit var selectedFollowerInfo: FollowerContent
        lateinit var followingAdapter: MenuFollowingListRVAdapter
        lateinit var followerAdapter: MenuFollowerListRVAdapter


        fun init(pos: Int) {
            this.pos = pos
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
            setFriendPageVp.setCurrentPage()
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
            binding.tieItemMenuFriendlist.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isTyping = true
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (binding.tieItemMenuFriendlist.text.toString() == "") {
                        followerList.clear()
                        followerAdapter.notifyDataSetChanged()
                        listSize = 0
                        page = 0
                        initV = true
                        initFollowerView()
                        return
                    }
                    if (checkValid(binding.tieItemMenuFriendlist.text.toString()) == 0) {
                        return
                    }
                    isTyping = false
                    startTimer("follower")
                    binding.lavMenu.visibility = View.VISIBLE
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
            binding.tieItemMenuFriendlist.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    isTyping = true
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (binding.tieItemMenuFriendlist.text.toString() == "") {
                        followingList.clear()
                        followingAdapter.notifyDataSetChanged()
                        listSize = 0
                        page = 0
                        initV = true
                        initFollowingView()
                    }
                    if (checkValid(binding.tieItemMenuFriendlist.text.toString()) == 0) {
                        return
                    }
                    isTyping = false
                    startTimer("following")
                    binding.lavMenu.visibility = View.VISIBLE
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
            MenuRetrofitService().getFollowerList((page+1).toString(), 10.toString(), this)
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
            setFriendPageVp.setNum(0, resp.data.page.totalElements)
        }

        override fun onFollowingListFailure() {
            TODO("Not yet implemented")
        }

        fun startTimer(type: String) {
            timer(period = 1) {
                time++
                if (!isTyping && time == 500) {
                    Log.d("keyword", binding.tieItemMenuFriendlist.text.toString())
                    if (type == "follower") {
                        MenuRetrofitService().searchFollower(binding.tieItemMenuFriendlist.text.toString(), this@FollowingPageHolder, type)
                    }
                    else {
                        MenuRetrofitService().searchFollowing(binding.tieItemMenuFriendlist.text.toString(), this@FollowingPageHolder, type)
                    }
                    time = 0
                    this.cancel()
                }
                if (isTyping || time == 500) {
                    time = 0
                    MenuFragmentManager.mainPage.activity?.runOnUiThread {
                        binding.lavMenu.visibility = View.GONE
                    }
                    this.cancel()
                }
            }
        }
        fun checkValid(str: String): Int {
            val keywordPattern = "^[가-힣A-Za-z\\d]{1,20}\$"
            val pattern = Pattern.compile(keywordPattern)
            val matcher = pattern.matcher(str)
            return if (matcher.find()) 1
            else 0
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
            setFriendPageVp.setNum(1, resp.data.page.totalElements)
        }

        override fun onFollowerListFailure() {
            TODO("Not yet implemented")
        }

        override fun onSearchFollowSuccess(resp: GlobalSearchSuccess, type: String) {
            if (type == "follower") {
                followerList.clear()
                for (i in resp.data!!.result) {
                    followerList.add(
                        FollowerContent(
                        follow = i.follow,
                        follower = Follower(
                            birth_date = i.member.birth_date,
                            username = i.member.username,
                            image_url = i.member.image_url,
                            name = i.member.name,
                            id = i.member.id
                            )
                        )
                    )
                }
                followerAdapter.notifyDataSetChanged()
            }
            else {
                followingList.clear()
                for (i in resp.data!!.result) {
                    followingList.add(
                        FollowingContent(
                            following = Following(
                                birth_date = i.member.birth_date,
                                username = i.member.username,
                                image_url = i.member.image_url,
                                name = i.member.name,
                                id = i.member.id
                            )
                        )
                    )
                }
                followingAdapter.notifyDataSetChanged()
            }
            binding.lavMenu.visibility = View.GONE
        }

        override fun onSearchFollowFailure() {
            TODO("Not yet implemented")
        }
    }
}