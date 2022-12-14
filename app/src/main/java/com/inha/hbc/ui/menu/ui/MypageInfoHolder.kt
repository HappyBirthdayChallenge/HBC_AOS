package com.inha.hbc.ui.menu.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.datatransport.runtime.scheduling.jobscheduling.SchedulerConfig.Flag
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.menu.GetProfileSuccess
import com.inha.hbc.databinding.ItemMyInfoBinding
import com.inha.hbc.ui.assist.dpToPx
import com.inha.hbc.ui.menu.view.AddFriendView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.MenuFragmentManager
import com.inha.hbc.util.network.menu.MenuRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.*

class MypageInfoHolder(val binding: ItemMyInfoBinding, val data: GetProfileSuccess):
    RecyclerView.ViewHolder(binding.root), AddFriendView{
    fun init() {
        initListener()
        initView()
    }

    fun initView() {
        Glide.with(MainFragmentManager.baseActivity.applicationContext).load(data.data.member.image_url).into(binding.ivItemMyInfoProfile)
        binding.tvItemMyInfoName.text = data.data.member.name
        val year = data.data.member.birth_date.year.toString()
        var month = data.data.member.birth_date.month.toString()
        if (month.length == 1) {
            month = "0$month"
        }
        var date = data.data.member.birth_date.date.toString()
        if (date.length == 1) {
            date = "0$date"
        }
        binding.tvItemMyInfoBirth.text = "$year.$month.$date"

        binding.tvItemMyInfoFollowing.text = data.data.followings.toString()
        binding.tvItemMyInfoFollower.text = data.data.followers.toString()

        binding.tvItemMyInfoLike.text = data.data.message_likes.toString()

        if (data.data.member.id == GlobalApplication.prefs.getInfo()!!.id) {
            binding.cvItemMyInfoFollow.visibility = View.GONE
            binding.cvItemMyInfoShare.visibility = View.VISIBLE
        }
        else {
            binding.cvItemMyInfoFollow.visibility = View.VISIBLE
            if (data.data.follow) {
                binding.tvItemMyInfoFollow.text = "?????????"
                binding.ivItemMyInfoFollow.visibility= View.VISIBLE
                binding.cvItemMyInfoFollow.strokeColor =
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.white, null)
                binding.cvItemMyInfoFollow.strokeWidth = dpToPx(0)
                binding.tvItemMyInfoFollow.setTextColor(
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
                )
            }
            else {
                binding.tvItemMyInfoFollow.text = "?????????"
                binding.ivItemMyInfoFollow.visibility= View.GONE
                binding.cvItemMyInfoFollow.strokeColor =
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.blue, null)
                binding.cvItemMyInfoFollow.strokeWidth = dpToPx(2)
                binding.tvItemMyInfoFollow.setTextColor(
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.blue, null)
                )
            }
        }



    }

    fun initListener() {
        binding.tvItemMyInfoFollower.setOnClickListener {
            MenuFragmentManager.openFriendList(1)
        }
        binding.tvItemMyInfoFollowerTitle.setOnClickListener {
            MenuFragmentManager.openFriendList(1)
        }
        binding.tvItemMyInfoFollowing.setOnClickListener {
            MenuFragmentManager.openFriendList(0)
        }
        binding.tvItemMyInfoFollowingTitle.setOnClickListener {
            MenuFragmentManager.openFriendList(0)
        }

        binding.cvItemMyInfoFollow.setOnClickListener {
            if (binding.tvItemMyInfoFollow.text == "?????????") {
                MenuRetrofitService().addFriend(data.data.member.id.toString(), this)
            }
        }
        binding.cvItemMyInfoShare.setOnClickListener {
            sendKakaoLink()
        }
    }

    fun sendKakaoLink() {
        // ????????? ????????? ????????? (?????????)
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "#?????? ???????????????",
                description = "?????? ????????? ??????????????????!",
                imageUrl = "https://drive.google.com/file/d/1YhCXRoaqz0HpSiDEgxENYCCHYhsPJ9UV/view?usp=share_link",
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.mtjin.nomoneytrip"
                )
            ),
            buttons = listOf(
                Button(
                    "????????? ??????",
                    Link(
                        androidExecutionParams = mapOf(
                            "key1" to "value1",
                            "key2" to "value2"
                        ) // ??? ????????? ????????????????????? ???????????? (??? ??????????????? ??????????????????)
                    )
                )
            )
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(MainFragmentManager.baseActivity.applicationContext)) {
            // ?????????????????? ???????????? ?????? ??????
            ShareClient.instance.shareDefault(MainFragmentManager.baseActivity.applicationContext, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.d("???????????? ?????? ??????", error.toString())
                }
                else if (sharingResult != null) {
                    Log.d("???????????? ?????? ??????", "???????????? ?????? ?????? ${sharingResult.intent}")
                    MainFragmentManager.baseActivity.startActivity(sharingResult.intent.addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK))

                    // ???????????? ????????? ??????????????? ?????? ?????? ???????????? ????????? ?????? ?????? ???????????? ?????? ???????????? ?????? ??? ????????????.
                    Log.d( "Warning","Warning Msg: ${sharingResult.warningMsg}")
                    Log.d("Argument" ,"Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            // ???????????? ?????????: ??? ?????? ?????? ??????
            // ??? ?????? ?????? ??????
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

            // CustomTabs?????? ??? ???????????? ??????

            // 1. CustomTabsServiceConnection ?????? ???????????? ??????
            // ex) Chrome, ?????? ?????????, FireFox, ?????? ???
            try {
                KakaoCustomTabsClient.openWithDefault(MainFragmentManager.baseActivity.applicationContext, sharerUrl)
            } catch(e: UnsupportedOperationException) {
                // CustomTabsServiceConnection ?????? ??????????????? ?????? ??? ????????????
            }

            // 2. CustomTabsServiceConnection ????????? ???????????? ??????
            // ex) ??????, ????????? ???
            try {
                KakaoCustomTabsClient.open(MainFragmentManager.baseActivity.applicationContext, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // ??????????????? ????????? ????????? ??????????????? ?????? ??? ????????????
            }
        }
    }

    override fun onAddFriendSuccess() {
        binding.tvItemMyInfoFollow.text = "?????????"
        binding.ivItemMyInfoFollow.visibility= View.VISIBLE
        binding.cvItemMyInfoFollow.strokeColor =
            MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.white, null)
        binding.cvItemMyInfoFollow.strokeWidth = dpToPx(0)
        binding.tvItemMyInfoFollow.setTextColor(
            MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
        )
    }

    override fun onAddFriendFailure() {
        TODO("Not yet implemented")
    }

}