package com.inha.hbc.ui.menu.ui

import android.content.ActivityNotFoundException
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        }
        else {
            binding.cvItemMyInfoFollow.visibility = View.VISIBLE
            if (data.data.follow) {
                binding.tvItemMyInfoFollow.text = "팔로잉"
                binding.ivItemMyInfoFollow.visibility= View.VISIBLE
                binding.cvItemMyInfoFollow.strokeColor =
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.white, null)
                binding.cvItemMyInfoFollow.strokeWidth = dpToPx(0)
                binding.tvItemMyInfoFollow.setTextColor(
                    MainFragmentManager.baseActivity.applicationContext.resources.getColor(R.color.black, null)
                )
            }
            else {
                binding.tvItemMyInfoFollow.text = "팔로우"
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
            if (binding.tvItemMyInfoFollow.text == "팔로우") {
                MenuRetrofitService().addFriend(data.data.member.id.toString(), this)
            }
        }
    }

    fun sendKakaoLink() {
        // 메시지 템플릿 만들기 (피드형)
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "#생일 축하챌린지",
                description = "함께 생일을 축하해주세요!",
                imageUrl = "https://drive.google.com/file/d/1YhCXRoaqz0HpSiDEgxENYCCHYhsPJ9UV/view?usp=share_link",
                link = Link(
                    mobileWebUrl = "https://play.google.com/store/apps/details?id=com.mtjin.nomoneytrip"
                )
            ),
            buttons = listOf(
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf(
                            "key1" to "value1",
                            "key2" to "value2"
                        ) // 내 앱에서 파라미터보낼건 필요없음 (앱 다운로드만 유도할것이다)
                    )
                )
            )
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(MainFragmentManager.baseActivity.applicationContext)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(MainFragmentManager.baseActivity.applicationContext, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.d("카카오톡 공유 실패", error.toString())
                }
                else if (sharingResult != null) {
                    Log.d("카카오톡 공유 성공", "카카오톡 공유 성공 ${sharingResult.intent}")
                    MainFragmentManager.baseActivity.startActivity(sharingResult.intent)

                    // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                    Log.d( "Warning","Warning Msg: ${sharingResult.warningMsg}")
                    Log.d("Argument" ,"Argument Msg: ${sharingResult.argumentMsg}")
                }
            }
        } else {
            // 카카오톡 미설치: 웹 공유 사용 권장
            // 웹 공유 예시 코드
            val sharerUrl = WebSharerClient.instance.makeDefaultUrl(defaultFeed)

            // CustomTabs으로 웹 브라우저 열기

            // 1. CustomTabsServiceConnection 지원 브라우저 열기
            // ex) Chrome, 삼성 인터넷, FireFox, 웨일 등
            try {
                KakaoCustomTabsClient.openWithDefault(MainFragmentManager.baseActivity.applicationContext, sharerUrl)
            } catch(e: UnsupportedOperationException) {
                // CustomTabsServiceConnection 지원 브라우저가 없을 때 예외처리
            }

            // 2. CustomTabsServiceConnection 미지원 브라우저 열기
            // ex) 다음, 네이버 등
            try {
                KakaoCustomTabsClient.open(MainFragmentManager.baseActivity.applicationContext, sharerUrl)
            } catch (e: ActivityNotFoundException) {
                // 디바이스에 설치된 인터넷 브라우저가 없을 때 예외처리
            }
        }
    }

    override fun onAddFriendSuccess() {
        binding.tvItemMyInfoFollow.text = "팔로잉"
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