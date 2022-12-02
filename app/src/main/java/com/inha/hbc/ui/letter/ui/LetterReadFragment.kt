package com.inha.hbc.ui.letter.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.data.remote.resp.message.GetMessageSuccess
import com.inha.hbc.databinding.FragmentLetterReadBinding
import com.inha.hbc.ui.adapter.LetterReadRVAdapter
import com.inha.hbc.ui.assist.serverAnimeToName
import com.inha.hbc.ui.letter.view.MessageLikeView
import com.inha.hbc.util.fragmentmanager.LetterReadManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.network.message.MessageRetrofitService
import kotlin.concurrent.thread
import kotlin.concurrent.timer

class LetterReadFragment(): Fragment(), MessageLikeView {
    lateinit var binding: FragmentLetterReadBinding
    lateinit var backPressedCallback: OnBackPressedCallback
    lateinit var resp: GetMessageSuccess
    var like = false
    var time = 0
    val handler = object : Handler(
        Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            binding.lavLetterReadLoading.visibility = View.GONE
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun init(resp: GetMessageSuccess) {
        this.resp = resp
        initView()
        initRv()
        initListener()
        binding.lavLetterReadLoading.setAnimation(serverAnimeToName(resp.data!!.animation_type))
        Log.d("frag", "fragclose")
        anime()
    }

    fun anime() {
        binding.lavLetterReadLoading.visibility = View.VISIBLE
        timer(period = 1000) {
            time++
            Log.d("frag", "time$time")
            if (time == 5) {
                handler.sendEmptyMessage(0)
                time = 0
                this.cancel()
            }
        }
    }

    fun initView() {
        binding.tvLetterReadTitle.text = resp.data!!.writer.name
        binding.tvLetterReadSubtitle.text = resp.data!!.writer.name + "의 메시지"

        Glide.with(MainFragmentManager.baseActivity.applicationContext).load(resp.data!!.writer.image_url).into(binding.ivLetterReadProfile)

        binding.tvLetterReadContent.text = resp.data!!.content


        if (resp.data!!.like) {
            binding.ivLetterReadHeart.setImageResource(R.drawable.ic_heart_full)
            like = true
        }
    }

    fun initRv() {
        val adapter = LetterReadRVAdapter(resp.data!!.file_uris)

        binding.rvLetterReadMedia.adapter = adapter
    }

    fun initListener() {
        binding.ivLetterReadHeart.setOnClickListener {
            if (!like) {
                MessageRetrofitService().messageLike(resp.data!!.message_id.toString(), this)
                binding.ivLetterReadHeart.setImageResource(R.drawable.ic_heart_full)
                like = true
            }
            else {
                Toast.makeText(context,"이미 좋아요를 보냈어요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLetterReadTextTitle.setOnClickListener {
            LetterReadManager.openTextDetail(resp.data!!.content)
        }
        binding.ivLetterReadTextGo.setOnClickListener {
            LetterReadManager.openTextDetail(resp.data!!.content)
        }
        binding.tvLetterReadContent.setOnClickListener {
            LetterReadManager.openTextDetail(resp.data!!.content)
        }

        binding.ivLetterReadBack.setOnClickListener {
            LetterReadManager.goMain()
        }
    }

    override fun onMessageLikeSuccess() {
        Toast.makeText(context,"좋아요를 보냈어요!", Toast.LENGTH_SHORT).show()
    }

    override fun onMessageLikeFailure() {
        TODO("Not yet implemented")
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                LetterReadManager.goMain()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }

}