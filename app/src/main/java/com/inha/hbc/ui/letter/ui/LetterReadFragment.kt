package com.inha.hbc.ui.letter.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.message.GetMessageSuccess
import com.inha.hbc.databinding.FragmentLetterReadBinding
import com.inha.hbc.ui.adapter.LetterReadRVAdapter
import com.inha.hbc.ui.assist.serverAnimeToName
import com.inha.hbc.ui.letter.view.MessageLikeView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.message.MessageRetrofitService

class LetterReadFragment(val resp: GetMessageSuccess, var open: Boolean): Fragment(), MessageLikeView {
    lateinit var binding: FragmentLetterReadBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRv()
        initListener()
    }

    fun anime() {
        binding.lavLetterReadLoading.visibility = View.VISIBLE
        binding.lavLetterReadLoading.setAnimation(serverAnimeToName(resp.data!!.animation_type))
        Thread.sleep(500)
        binding.lavLetterReadLoading.visibility = View.GONE
    }

    fun initView() {
        binding.tvLetterReadTitle.text = resp.data!!.member.name
        binding.tvLetterReadSubtitle.text = resp.data!!.member.name + "의 메시지"

        Glide.with(MainFragmentManager.baseActivity.applicationContext).load(resp.data!!.member.image_url).into(binding.ivLetterReadProfile)

        binding.tvLetterReadContent.text = resp.data!!.content
    }

    fun initRv() {
        val adapter = LetterReadRVAdapter(resp.data!!.file_uris)

        binding.rvLetterReadMedia.adapter = adapter
    }

    fun initListener() {
        binding.ivLetterReadHeart.setOnClickListener {
            MessageRetrofitService().messageLike(resp.data!!.message_id.toString(), this)
        }
    }

    override fun onMessageLikeSuccess() {
        binding.ivLetterReadHeart.setImageResource(R.drawable.ic_heart_full)
    }

    override fun onMessageLikeFailure() {
        TODO("Not yet implemented")
    }
}