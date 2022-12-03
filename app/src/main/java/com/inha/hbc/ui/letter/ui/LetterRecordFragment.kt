package com.inha.hbc.ui.letter.ui

import android.content.ContentValues
import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.MediaRecorder.AudioEncoder
import android.media.MediaRecorder.AudioSource
import android.media.MediaRecorder.OutputFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieDrawable
import com.inha.hbc.R
import com.inha.hbc.data.local.RecordingState
import com.inha.hbc.data.remote.resp.message.UploadSuccess
import com.inha.hbc.databinding.FragmentLetterRecordBinding
import com.inha.hbc.ui.letter.view.UploadView
import com.inha.hbc.util.fragmentmanager.LetterFragmentManager
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.io.File
import java.util.Timer
import kotlin.concurrent.timer

class LetterRecordFragment: Fragment(), UploadView {
    lateinit var binding: FragmentLetterRecordBinding
    lateinit var backPressedCallback: OnBackPressedCallback
    var timer: Timer? = null
    var time = 0

    var state = RecordingState.BEFORE_RECORDING

    var playing = false
    lateinit var filename:String
    lateinit var filePath :String
    lateinit var fileUri: Uri
    lateinit var file: File
    private val recorder = if (Build.VERSION.SDK_INT >= 31) {
        MediaRecorder(MainFragmentManager.baseActivity.baseContext)
    }
    else {
        MediaRecorder()
    }

    private val player = MediaPlayer()
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
        initTimer()
        initListener()

        val a = resources.assets.open("json_deco_anime_1.json")
    }

    fun initRecorder() {
        recorder.apply {
            setAudioSource(AudioSource.MIC)
            setOutputFormat(OutputFormat.MPEG_4)
            setAudioEncoder(AudioEncoder.AAC_ELD)
            val sdf = java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
            filename = sdf.format(System.currentTimeMillis()) + ".m4a"
            file = File(
                File(requireContext().filesDir.toString() + "/record").apply {
                    if (!this.exists()) {
                        this.mkdirs()
                    }
                },
                filename
            )
            filePath = file.toString()
            setOutputFile(filePath)
            prepare()
        }
    }

    fun startPlay() {
        player.apply {
            setDataSource(filePath)
            prepare()
        }
        player.start()
        playing = true
        binding.ivLetterRecordPlay.setImageResource(R.drawable.ic_letter_record_pause)
        binding.lavLetterRecordState.playAnimation()
        binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
    }

    fun stopPlay() {
        player.reset()
        playing = false
        binding.ivLetterRecordPlay.setImageResource(R.drawable.ic_letter_record_play)
        binding.lavLetterRecordState.pauseAnimation()
        binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
    }

    fun initTimer() {
        timer = timer(period = 1000) {
            if (state == RecordingState.PAUSE_RECORDING ||
                    state == RecordingState.BEFORE_RECORDING ||
                    state == RecordingState.AFTER_RESET) {
                while(true) {
                    if (state == RecordingState.ON_RECORDING) {
                        break
                    }
                    if (state == RecordingState.AFTER_RECORDING) {
                        while (true) {
                            if (state == RecordingState.AFTER_RESET) {

                                time = 0
                                activity?.runOnUiThread {
                                    binding.tvLetterRecordTime.text = "00:00"
                                }
                                break
                            }
                        }
                    }
                }
                return@timer
            }
            if (state == RecordingState.AFTER_RECORDING) {
                while (true) {
                    if (state == RecordingState.AFTER_RESET) {

                        time = 0
                        activity?.runOnUiThread {
                            binding.tvLetterRecordTime.text = "00:00"
                        }
                        break
                    }
                }
                return@timer
            }
            time++

            var s = time % 60
            var ss = if (s < 10) {
                "0$s"
            }
            else {
                "$s"
            }

            var m = time / 60
            var mm = if (m < 10) {
                "0$m"
            }
            else {
                "$m"
            }

            activity?.runOnUiThread {
                binding.tvLetterRecordTime.text = "$mm:$ss"
            }
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
                    binding.ivLetterRecordReset.isEnabled = false
                    binding.ivLetterRecordStop.isEnabled = true


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
                    binding.ivLetterRecordStop.isEnabled = true
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
                    binding.ivLetterRecordReset.isEnabled = false
                    binding.ivLetterRecordStop.isEnabled = true
                }
                RecordingState.AFTER_RESET -> {
                    recorder.start()

                    state = RecordingState.ON_RECORDING
                    binding.tvLetterRecordState.text = "녹음 중"
                    binding.lavLetterRecordState.playAnimation()
                    binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
                    binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_pause)
                    binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
                    binding.ivLetterRecordPlay.visibility = View.GONE
                    binding.ivLetterRecordReset.isEnabled = false
                    binding.ivLetterRecordStop.isEnabled = true
                }
                else -> {//일시정지
                    recorder.pause()

                    state = RecordingState.PAUSE_RECORDING
                    binding.tvLetterRecordState.text = "일시정지"
                    binding.lavLetterRecordState.pauseAnimation()
                    binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
                    binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_start)
                    binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
                    binding.ivLetterRecordPlay.visibility = View.GONE
                    binding.ivLetterRecordReset.isEnabled = true
                    binding.ivLetterRecordStop.isEnabled = true
                }
            }
        }
        binding.ivLetterRecordReset.setOnClickListener {
            recorder.reset()

            initRecorder()

            state = RecordingState.AFTER_RESET
            binding.tvLetterRecordState.text = "버튼을 눌러\n녹음을 시작해주세요"
            binding.lavLetterRecordState.pauseAnimation()
            binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
            binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_start)
            binding.lavLetterRecordState.setPadding(0, 0, 0, 0)
            binding.ivLetterRecordPlay.visibility = View.GONE
            binding.ivLetterRecordReset.isEnabled = false
            binding.ivLetterRecordStop.isEnabled = false
            binding.ivLetterRecordRecording.isEnabled= true
        }

        binding.ivLetterRecordStop.setOnClickListener {
            recorder.stop()

            state = RecordingState.AFTER_RECORDING
            binding.tvLetterRecordState.text = "녹음완료!"
            binding.lavLetterRecordState.pauseAnimation()
            binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
            binding.ivLetterRecordRecording.setImageResource(R.drawable.ic_letter_record_start)
            binding.lavLetterRecordState.setPadding(dpToPx(35), 0, 0, 0)
            binding.ivLetterRecordPlay.visibility = View.VISIBLE
            binding.ivLetterRecordStop.isEnabled = false
            binding.ivLetterRecordReset.isEnabled = true
            binding.ivLetterRecordRecording.isEnabled = false
        }
        binding.ivLetterRecordPlay.setOnClickListener {
            if (playing) {
                stopPlay()
            }
            else {
                startPlay()
            }
        }
        player.setOnCompletionListener {
            stopPlay()
        }

        binding.tvLetterRecordSubmit.setOnClickListener {
            if (state == RecordingState.AFTER_RECORDING) {
                if (file.length()/1024 <= 10 * 1024) {
                    makeUri()
                    LetterFragmentManager.recordClose(filePath, fileUri, this)
                }
                else {
                    Toast.makeText(requireContext(), "지원용량을 초과했어요!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ivLetterRecordBack.setOnClickListener {
            LetterFragmentManager.recordClose(this)
        }
    }

    fun dpToPx(int: Int): Int {
        val density = resources.displayMetrics.density
        return (int * density).toInt()
    }

    fun makeUri() {
        val value = ContentValues(2)
        value.put(MediaStore.Audio.Media.DATA, filePath)
        value.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4")

        fileUri = activity?.contentResolver!!.insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, value)!!
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                LetterFragmentManager.recordClose(this@LetterRecordFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }

    override fun onUploadSuccess(resp: UploadSuccess) {
    }

    override fun onUploadFailure() {
        TODO("Not yet implemented")
    }

}