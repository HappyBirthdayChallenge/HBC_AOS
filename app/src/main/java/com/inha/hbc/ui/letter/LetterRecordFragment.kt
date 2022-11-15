package com.inha.hbc.ui.letter

import android.media.MediaRecorder
import android.media.MediaRecorder.AudioEncoder
import android.media.MediaRecorder.AudioSource
import android.media.MediaRecorder.OutputFormat
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieDrawable
import com.inha.hbc.R
import com.inha.hbc.data.local.RecordingState
import com.inha.hbc.databinding.FragmentLetterRecordBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.io.File
import java.sql.Timestamp

class LetterRecordFragment: Fragment() {
    lateinit var binding: FragmentLetterRecordBinding

    var state = RecordingState.BEFORE_RECORDING
    private val filePath :String by lazy {
        val sdf = java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis()) + ".m4a"
        "${
            File(
                File(requireContext().filesDir.toString() + "/record").apply {
                    if (!this.exists()) {
                        this.mkdirs()
                    }
                },
                filename
            )
        }"
    }
    val recorder = if (Build.VERSION.SDK_INT >= 31) {
        MediaRecorder(MainFragmentManager.baseActivity.baseContext)
    }
    else {
        MediaRecorder()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterRecordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecorder()

        initListener()
    }

    fun initRecorder() {
        recorder.apply{
            setAudioSource(AudioSource.MIC)
            setOutputFormat(OutputFormat.MPEG_4)
            setAudioEncoder(AudioEncoder.AAC_ELD)
            setOutputFile(filePath)
            prepare()
        }
    }

    fun initListener() {
        binding.ivLetterRecordReset.isEnabled = false
        binding.ivLetterRecordRecording.setOnClickListener {
            when(state) {
                RecordingState.BEFORE_RECORDING -> {
                    recorder.start()

                    state = RecordingState.ON_RECORDING
                    binding.tvLetterRecordState.text = "녹음 중"
                    binding.lavLetterRecordState.playAnimation()
                    binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
                    binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_pause)
                    binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
                    binding.ivLetterRecordPlay.visibility = View.GONE
                }
                RecordingState.PAUSE_RECORDING -> {
                    recorder.resume()

                    state = RecordingState.ON_RECORDING
                    binding.tvLetterRecordState.text = "녹음 중"
                    binding.lavLetterRecordState.playAnimation()
                    binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
                    binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_pause)
                    binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
                    binding.ivLetterRecordPlay.visibility = View.GONE
                    binding.ivLetterRecordReset.isEnabled = false
                }
                RecordingState.AFTER_RECORDING -> {
                    recorder.start()

                    state = RecordingState.ON_RECORDING
                    binding.tvLetterRecordState.text = "녹음 중"
                    binding.lavLetterRecordState.playAnimation()
                    binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
                    binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_pause)
                    binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
                    binding.ivLetterRecordPlay.visibility = View.GONE
                }
                else -> {//일시정지
                    recorder.pause()

                    state = RecordingState.PAUSE_RECORDING
                    binding.tvLetterRecordState.text = "일시정지"
                    binding.lavLetterRecordState.pauseAnimation()
                    binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
                    binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_start)
                    binding.lavLetterRecordState.setPadding(dpToPx(35), 0, 0, 0)
                    binding.ivLetterRecordPlay.visibility = View.VISIBLE
                    binding.ivLetterRecordReset.isEnabled = true
                }
            }
        }
        binding.ivLetterRecordReset.setOnClickListener {
            recorder.reset()

            state = RecordingState.BEFORE_RECORDING
            binding.tvLetterRecordState.text = "버튼을 눌러\n녹음을 시작해주세요"
            binding.lavLetterRecordState.pauseAnimation()
            binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
            binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_start)
            binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
            binding.ivLetterRecordPlay.visibility = View.GONE
            binding.ivLetterRecordReset.isEnabled = false
        }

        binding.ivLetterRecordStop.setOnClickListener {
            recorder.stop()

            state = RecordingState.AFTER_RECORDING
            binding.tvLetterRecordState.text = "녹음완료!"
            binding.lavLetterRecordState.pauseAnimation()
            binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
            binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_start)
            binding.lavLetterRecordState.setPadding(dpToPx(35), 0, 0, 0)
            binding.ivLetterRecordPlay.visibility = View.GONE
            binding.ivLetterRecordStop.isEnabled = false


        }
    }

    fun dpToPx(int: Int): Int {
        val density = resources.displayMetrics.density
        return (int * density).toInt()
    }
}