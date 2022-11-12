package com.inha.hbc.ui.letter

import android.content.Context
import android.content.Intent
import android.content.res.Resources.Theme
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.inha.hbc.R
import com.inha.hbc.databinding.FragmentLetterBaseBinding
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
            imgURI = it.data?.data!!
            Log.d("imgUri", imgURI.toString())
        }

    val cam = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.d("imgUri", imgURI.toString())
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
    }

    override fun onResume() {
        super.onResume()

        MainFragmentManager.viewWidth = binding.clLetterBase.rootView.width
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
            add("카메라")
            add("앨범")
            add("녹음")
        }

    }

    fun initListener() {
        binding.ivLetterBaseClose.setOnClickListener {
            MainFragmentManager.letterClose()
        }

        binding.tlLetterBase.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (step[tab.position]) {
                    binding.vpLetterBase.currentItem = tab.position
                    binding.tlLetterBase.setSelectedTabIndicatorColor(resources.getColor(R.color.main_color, null))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })



        binding.tvLetterBaseAddBackground.setOnClickListener {
            binding.tvLetterBaseAddBackground.visibility = View.GONE
            binding.rvLetterBaseAddMenu.visibility = View.GONE

//            binding.fabLetterSend.show()
//            binding.fabLetterAdd.show()
        }

    }


    fun initRv() {
        val menuRVAdapter = LetterMenuRVAdapter(menuData)
        menuRVAdapter.onlistener = object : LetterMenuRVAdapter.OnListener {
            override fun onClick(pos: Int) {
                closeList()


                when (menuData[pos]) {
                    "카메라" -> {
                        openCamera()
                    }
                    "앨범" -> {
                        openGallery()
                    }
                    else -> {
                        openRecord()
                    }
                }
            }
        }

        binding.rvLetterBaseAddMenu.adapter = menuRVAdapter

        val vpAdapter = LetterBaseVPAdapter(fragmentArr)
        binding.vpLetterBase.adapter = vpAdapter
    }


    fun openList() {
        binding.tvLetterBaseAddBackground.visibility = View.VISIBLE
        binding.rvLetterBaseAddMenu.visibility = View.VISIBLE
    }

    fun closeList() {
        binding.tvLetterBaseAddBackground.visibility = View.GONE
        binding.rvLetterBaseAddMenu.visibility = View.GONE
    }

    fun openRecord() {

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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgURI)
        cam.launch(intent)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        gal.launch(intent)
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

//                    binding.fabLetterSend.show()
//                    binding.fabLetterAdd.show()
                }

                else if (binding.vpLetterBase.currentItem == 2) {
                    binding.vpLetterBase.currentItem = 1

                }
                else if (binding.vpLetterBase.currentItem == 1) {
                    binding.vpLetterBase.currentItem = 0
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