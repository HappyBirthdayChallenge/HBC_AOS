package com.inha.hbc.ui.letter

import android.content.ContentValues
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.media.MediaRecorder.AudioEncoder
import android.media.MediaRecorder.AudioSource
import android.media.MediaRecorder.OutputFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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
import java.util.Timer
import kotlin.concurrent.timer

class LetterRecordFragment: Fragment() {
    lateinit var binding: FragmentLetterRecordBinding
    var timer: Timer? = null
    var time = 0

    var state = RecordingState.BEFORE_RECORDING
    var playing = false
    lateinit var filename:String
    lateinit var filePath :String
    lateinit var fileUri: Uri
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
    }

    fun initRecorder() {
        recorder.apply {
            setAudioSource(AudioSource.MIC)
            setOutputFormat(OutputFormat.MPEG_4)
            setAudioEncoder(AudioEncoder.AAC_ELD)
            val sdf = java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
            filename = sdf.format(System.currentTimeMillis()) + ".m4a"
            filePath = File(
                File(requireContext().filesDir.toString() + "/record").apply {
                    if (!this.exists()) {
                        this.mkdirs()
                    }
                },
                filename
            ).toString()
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

    fun pausePlay() {
        player.release()
        playing = false
        binding.ivLetterRecordPlay.setImageResource(R.drawable.ic_letter_record_play)
        binding.lavLetterRecordState.pauseAnimation()
        binding.lavLetterRecordState.repeatCount = LottieDrawable.INFINITE
    }

    fun initTimer() {
        timer = timer(period = 1000) {
            if (state == RecordingState.PAUSE_RECORDING ||
                    state == RecordingState.BEFORE_RECORDING) {
                while(true) {
                    if (state == RecordingState.ON_RECORDING) {
                        break
                    }
1                }
                return@timer
            }
            if (state == RecordingState.AFTER_RECORDING) {
                while(true) {
                    if (state == RecordingState.AFTER_RESET) {

                        activity?.runOnUiThread {
                            time = 0
                            binding.tvLetterRecordTime.text = "0:00"
                            binding.tvLetterRecordSize.text = "0.00/50mb"
                        }
                        break
                    }
                }
                while(true) {
                    if (state == RecordingState.ON_RECORDING) {
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


            var kbSize = (File(filePath).length()/1024).toDouble()
            var mbSize: Double = kbSize/1024
            var mbSizeStr = String.format("%.2f", mbSize)


            activity?.runOnUiThread {
                binding.tvLetterRecordTime.text = "$mm:$ss"
                binding.tvLetterRecordSize.text = "$mbSizeStr/50mb"
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
                    binding.lavLetterRecordState.setPadding(dpToPx(35), 0, 0, 0)
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
                pausePlay()
            }
            else {
                startPlay()
            }
        }
        player.setOnCompletionListener {
            pausePlay()
        }

        binding.tvLetterRecordSubmit.setOnClickListener {
            if (state == RecordingState.AFTER_RECORDING) {
                makeUri()
                MainFragmentManager.recordClose(fileUri, this)
            }
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
}