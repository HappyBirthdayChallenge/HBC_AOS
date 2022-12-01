package com.inha.hbc.ui.letter.ui

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.inha.hbc.R
import com.inha.hbc.databinding.FragmentLetterShowBinding
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.LetterReadManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager

class LetterReadObjectFragment(val url: String, val type:Int): Fragment() {
    lateinit var binding: FragmentLetterShowBinding
    lateinit var backPressedCallback: OnBackPressedCallback
    var playing = false
    val player = MediaPlayer()
    var stop = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
        initView()
    }

    fun initListener() {
        binding.ivLetterShowBack.setOnClickListener {
            LetterReadManager.closeShow(this)
        }
        binding.ivLetterShowPlay.setOnClickListener {
            if (playing) {
                pausePlayer()
            }
            else {
                startPlayer()
            }
        }

        binding.ivLetterShowReset.setOnClickListener {
            pausePlayer()
            player.stop()
            stop = true
            binding.ivLetterShowReset.isEnabled = false
        }

        player.setOnCompletionListener {
            if (player != null) {
                pausePlayer()
            }
        }
    }

    fun initView() {
        binding.tvLetterShowTitle.text = LetterReadManager.senderId
        //0 동영상
        //1 사진
        //2 음성
        //3 텍스트
        when(type) {
            0 -> {//비디오
                binding.ivLetterShow.visibility = View.GONE

                binding.ivLetterShowReset.visibility = View.GONE
                binding.ivLetterShowPlay.visibility = View.GONE
                binding.lavLetterShowRecord.visibility = View.GONE

                binding.clLetterShow.visibility = View.VISIBLE

                binding.vvLetterShow.setVideoPath(url)
                binding.vvLetterShow.setMediaController(MediaController(requireContext()))
                binding.vvLetterShow.requestFocus()
                binding.vvLetterShow.setOnPreparedListener{mp: MediaPlayer ->
                    val videoRatio = mp.videoWidth / mp.videoHeight.toFloat()
                    val screenRatio =  binding.vvLetterShow.width /  binding.vvLetterShow.height.toFloat()
                    val scaleX = videoRatio / screenRatio
                    if (scaleX >= 1f) {
                        binding.vvLetterShow.scaleX = scaleX
                    } else {
                        binding.vvLetterShow.scaleY = 1f / scaleX
                    }
//                    mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
                    mp.isLooping = true
                }
                binding.vvLetterShow.start()
            }
            2 -> {//음성
                binding.ivLetterShow.visibility = View.GONE

                binding.ivLetterShowReset.visibility = View.VISIBLE
                binding.ivLetterShowPlay.visibility = View.VISIBLE
                binding.lavLetterShowRecord.visibility = View.VISIBLE

                binding.vvLetterShow.visibility = View.GONE
                player.setDataSource(url)
            }
            3 -> {
                binding.tvLetterShow.visibility = View.VISIBLE
                binding.tvLetterShow.text = url
            }
            else -> {
                Glide.with(MainFragmentManager.baseActivity.applicationContext).load(url)
                    .into(binding.ivLetterShow)
            }
        }
    }

    fun startPlayer() {
        binding.ivLetterShowReset.isEnabled = false
        if (stop) {
            player.prepare()
            stop = false
        }
        player.start()
        playing = true
        binding.ivLetterShowPlay.setImageResource(R.drawable.ic_letter_record_pause)
        binding.lavLetterShowRecord.playAnimation()
    }

    fun pausePlayer() {
        binding.ivLetterShowReset.isEnabled = true
        player.pause()
        playing = false
        binding.ivLetterShowPlay.setImageResource(R.drawable.ic_letter_record_play)
        binding.lavLetterShowRecord.pauseAnimation()

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                LetterReadManager.closeShow(this@LetterReadObjectFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }


}