package com.inha.hbc.ui.letter.ui

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.inha.hbc.R
import com.inha.hbc.databinding.FragmentLetterShowBinding
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager


class LetterShowFragment(val pos: Int): Fragment() {
    lateinit var binding: FragmentLetterShowBinding
    lateinit var backPressedCallback: OnBackPressedCallback
    var playing = false
    lateinit var player: MediaPlayer
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

    fun initView() {
        val type = LetterFragmentManager.fileInfo[pos].type
        val uri = LetterFragmentManager.fileInfo[pos].uri
        when(type) {
            1 -> {//비디오
                binding.ivLetterShow.visibility = View.GONE

                binding.ivLetterShowReset.visibility = View.GONE
                binding.ivLetterShowPlay.visibility = View.GONE
                binding.lavLetterShowRecord.visibility = View.GONE

                binding.clLetterShow.visibility = View.VISIBLE

                binding.vvLetterShow.setVideoURI(uri)
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
            }
            else -> {
                binding.ivLetterShow.setImageURI(uri)
            }
        }
    }

    fun initListener() {
        binding.ivLetterShowBack.setOnClickListener{
            LetterFragmentManager.closeShow(this)
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

        }
    }

    fun startPlayer() {
        player = MediaPlayer()
        player.setDataSource(MainFragmentManager.baseActivity.baseContext, LetterFragmentManager.fileInfo[pos].uri)
        player.start()
        playing = true
        binding.ivLetterShowPlay.setImageResource(R.drawable.ic_letter_record_pause)
    }

    fun pausePlayer() {
        player.pause()
        playing = false
        binding.ivLetterShowPlay.setImageResource(R.drawable.ic_letter_record_play)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                LetterFragmentManager.closeShow(this@LetterShowFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }

}