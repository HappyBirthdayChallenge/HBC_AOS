package com.inha.hbc.ui.letter.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inha.hbc.data.remote.resp.message.GetMessageSuccess
import com.inha.hbc.databinding.FragmentLetterReadBinding
import com.inha.hbc.ui.adapter.LetterReadRVAdapter
import com.inha.hbc.ui.assist.serverAnimeToName
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterReadFragment(val resp: GetMessageSuccess): Fragment() {
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

        anime()
        initView()
        initRv()
    }

    fun anime() {
        binding.lavLetterReadLoading.setAnimation(resp.data!!.animation_type)
        Thread.sleep(500)
        binding.lavLetterReadLoading.visibility = View.GONE
    }

    fun initView() {
        binding.tvLetterReadTitle.text = resp.data!!.member.name
        binding.tvLetterReadSubtitle.text = resp.data!!.member.name + "의 메시지"

        Glide.with(MainFragmentManager.baseActivity.applicationContext).load(resp.data!!.member.image_url).into(binding.ivLetterReadProfile)
    }

    fun initRv() {
        val adapter = LetterReadRVAdapter(resp.data!!.file_uris)

        binding.rvLetterReadMedia.adapter = adapter
    }

    fun initListener() {
        binding.ivLetterReadHeart.setOnClickListener {
            
        }
    }
}