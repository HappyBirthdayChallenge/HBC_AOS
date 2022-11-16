package com.inha.hbc.ui.letter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources.Theme
import android.graphics.Bitmap
import android.graphics.Paint
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.core.content.FileProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.inha.hbc.R
import com.inha.hbc.databinding.FragmentLetterBaseBinding
import com.inha.hbc.databinding.ItemTabSelectedBinding
import com.inha.hbc.ui.adapter.LetterBaseVPAdapter
import com.inha.hbc.ui.adapter.LetterMenuRVAdapter
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.io.File
import java.lang.reflect.Modifier
import java.net.URI
import java.util.*
import kotlin.collections.ArrayList

class LetterBaseFragment: Fragment() {
    lateinit var binding: FragmentLetterBaseBinding
    var fragmentArr = ArrayList<Int>()
    var step = ArrayList<Boolean>()
    val menuData = ArrayList<String>()


    lateinit var backPressedCallback: OnBackPressedCallback


    lateinit var imgPath: String
    lateinit var imgURI: Uri

    val gal =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.data?.data != null) {
                imgURI = it.data?.data!!

                var columnIdx = 0
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = MainFragmentManager.baseActivity.contentResolver.query(imgURI, proj, null, null, null)
                if (cursor!!.moveToFirst()) {
                    columnIdx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                }
                imgPath = cursor.getString(columnIdx)
                cursor.close()


                val fileType = imgPath.substring(imgPath.length - 3, imgPath.length)
                if (fileType == "mp4") {
                    MainFragmentManager.updateData(imgURI, 1, imgPath)
                }
                else if (fileType == "jpg" || fileType == "png" || fileType == "peg" || fileType == "gif"){
                    MainFragmentManager.updateData(imgURI, 0, imgPath)
                    Log.d("imgUri", imgURI.toString())
                }
                else {

                }
            }
        }

    val cam = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val file = File(imgPath)
        val fileSize = file.length().toInt()
        val fileType = file.path.substring(file.path.length - 3, file.path.length)
        if (fileSize != 0) {
            if (fileType == "mp4") {
                MainFragmentManager.updateData(imgURI, 1, imgPath)
            }
            else {
                MainFragmentManager.updateData(imgURI, 0, imgPath)
                Log.d("imgUri", imgURI.toString())
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArr()
        initListener()
        initRv()
        initView()
    }

    override fun onResume() {
        super.onResume()

        MainFragmentManager.viewWidth = binding.clLetterBase.rootView.width
    }

    fun initView() {
        val text = SpannableString(binding.tlLetterBase.getTabAt(0)!!.text)
        text.setSpan(UnderlineSpan(), 0, text.length, 0)
        binding.tlLetterBase.getTabAt(0)!!.text = text

        binding.vpLetterBase.isUserInputEnabled = false


    }

    fun initArr() {
        step.apply {
            add(true)
            add(false)
            add(false)
        }

        fragmentArr.apply {
            add(1)
            add(1)
            add(2)
        }

        menuData.apply {
            add("사진")
            add("동영상")
            add("앨범")
            add("녹음")
        }

    }

    fun initListener() {
        binding.ivLetterBaseBack.setOnClickListener {
            if (binding.tvLetterBaseAddBackground.visibility == View.VISIBLE) {
                binding.tvLetterBaseAddBackground.visibility = View.GONE
                binding.rvLetterBaseAddMenu.visibility = View.GONE

            }

            else if (binding.vpLetterBase.currentItem == 2) {
                binding.vpLetterBase.currentItem = 1
                binding.tlLetterBase.selectTab(binding.tlLetterBase.getTabAt(1))

            }
            else if (binding.vpLetterBase.currentItem == 1) {
                binding.vpLetterBase.currentItem = 0
                binding.tlLetterBase.selectTab(binding.tlLetterBase.getTabAt(0))
            }
            else {
                MainFragmentManager.letterClose()
            }
        }

        binding.tlLetterBase.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (step[tab.position]) {

                    tab.customView = null
                    tab.customView = LayoutInflater.from(MainFragmentManager.baseActivity.baseContext)
                        .inflate(R.layout.item_tab_selected, null)
                    val tabBinding = ItemTabSelectedBinding.bind(tab.customView!!)

                    binding.vpLetterBase.currentItem = tab.position
                    tabBinding.tvItemTab.setTextColor(MainFragmentManager.baseActivity.getColor(R.color.main_color))
                    tabBinding.tvItemTab.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                    tabBinding.tvItemTab.text = getTabText(tab.position)


                    binding.tvLetterBaseSend.visibility = View.GONE
                    binding.ivLetterBaseSend.visibility = View.GONE

                    if (tab.position == 2) {
                        binding.tvLetterBaseSend.visibility = View.VISIBLE
                        binding.ivLetterBaseSend.visibility = View.VISIBLE
                        MainFragmentManager.letterFragment.notifyUpdate()
                    }
                }
                else {
                    tab.parent!!.selectTab(tab.parent!!.getTabAt(binding.vpLetterBase.currentItem))
                    Toast.makeText(MainFragmentManager.baseActivity.baseContext, "이전단계를 완료해주세요!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                if (step[tab.position]) {
                    tab.customView = null
                    tab.customView = LayoutInflater.from(MainFragmentManager.baseActivity.baseContext)
                        .inflate(R.layout.item_tab_selected, null)
                    val tabBinding = ItemTabSelectedBinding.bind(tab.customView!!)
                    tabBinding.tvItemTab.setTextColor(MainFragmentManager.baseActivity.getColor(R.color.black))
                    tabBinding.tvItemTab.text = getTabText(tab.position)
                }
                else {

                }

            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })



        binding.tvLetterBaseAddBackground.setOnClickListener {
            binding.tvLetterBaseAddBackground.visibility = View.GONE
            binding.rvLetterBaseAddMenu.visibility = View.GONE

        }

    }

    fun getTabText(pos: Int): String {
        return when(pos) {
            0-> "장식품"
            1-> "효과"
            else -> "편지"
        }
    }


    fun initRv() {
        val menuRVAdapter = LetterMenuRVAdapter(menuData)
        menuRVAdapter.onlistener = object : LetterMenuRVAdapter.OnListener {
            override fun onClick(pos: Int) {
                closeList()
                when (menuData[pos]) {
                    "사진" -> {
                        openCamera()
                    }
                    "앨범" -> {
                        openGallery()
                    }
                    "동영상" -> {
                        openVideo()
                    }
                    else -> {
                        MainFragmentManager.openRecording()
                    }
                }
            }
        }

        binding.rvLetterBaseAddMenu.adapter = menuRVAdapter

        val vpAdapter = LetterBaseVPAdapter(fragmentArr)
        binding.vpLetterBase.adapter = vpAdapter
    }

    fun getObject() {
        step[1] = true
        binding.vpLetterBase.currentItem = 1
        binding.tlLetterBase.selectTab(binding.tlLetterBase.getTabAt(1))
    }

    fun getAnime() {
        step[2] = true
        MainFragmentManager.letterFragment.notifyUpdate()
        binding.vpLetterBase.currentItem = 2
        binding.tlLetterBase.selectTab(binding.tlLetterBase.getTabAt(2))
    }


    fun openList() {
        binding.tvLetterBaseAddBackground.visibility = View.VISIBLE
        binding.rvLetterBaseAddMenu.visibility = View.VISIBLE
    }

    fun closeList() {
        binding.tvLetterBaseAddBackground.visibility = View.GONE
        binding.rvLetterBaseAddMenu.visibility = View.GONE

    }


    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val sdf = java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis()) + ".jpg"
        //val photoFile = createTmpFile()!!
        val photoFile = File(
            File(requireContext().filesDir.toString() + "/image").apply{
                if (!this.exists()) {
                    this.mkdirs()
                }
            },
            filename
        )
        imgURI = FileProvider.getUriForFile(
            requireContext(), "com.inha.hbc.fileprovider", photoFile
        )
        imgPath = photoFile.absolutePath
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgURI)
        cam.launch(intent)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/* video/*"
        gal.launch(intent)
    }

    fun openVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val sdf = java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis()) + ".mp4"
        //val photoFile = createTmpFile()!!
        val photoFile = File(
            File(requireContext().filesDir.toString() + "/video").apply{
                if (!this.exists()) {
                    this.mkdirs()
                }
            },
            filename
        )
        imgURI = FileProvider.getUriForFile(
            requireContext(), "com.inha.hbc.fileprovider", photoFile
        )
        imgPath = photoFile.absolutePath
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgURI)
        cam.launch(intent)
    }



    fun createTmpFile(): File? {
        val timeStamp = java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imgFileName = "JPEG_$timeStamp.jpg"
        var imgFile: File? = null
        val fileDir = File(
            Environment.getDataDirectory().toString() + "/Pictures", "hbc"
        )
        imgFile = File (fileDir, imgFileName)
        imgPath = imgFile.absolutePath
        return imgFile
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.tvLetterBaseAddBackground.visibility == View.VISIBLE) {
                    binding.tvLetterBaseAddBackground.visibility = View.GONE
                    binding.rvLetterBaseAddMenu.visibility = View.GONE

                }

                else if (binding.vpLetterBase.currentItem == 2) {
                    binding.vpLetterBase.currentItem = 1
                    binding.tlLetterBase.selectTab(binding.tlLetterBase.getTabAt(1))

                }
                else if (binding.vpLetterBase.currentItem == 1) {
                    binding.vpLetterBase.currentItem = 0
                    binding.tlLetterBase.selectTab(binding.tlLetterBase.getTabAt(0))
                }
                else {
                    MainFragmentManager.letterClose()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onDetach() {
        super.onDetach()
        backPressedCallback.remove()
    }
}