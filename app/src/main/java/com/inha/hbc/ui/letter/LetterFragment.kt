package com.inha.hbc.ui.letter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.inha.hbc.databinding.FragmentLetterBinding
import com.inha.hbc.ui.adapter.LetterMenuRVAdapter
import com.inha.hbc.ui.adapter.LetterObjectRVAdapter
import com.inha.hbc.ui.dialog.LetterDialog
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.io.File
import java.util.Date

class LetterFragment: Fragment() {

    lateinit var backPressedCallback: OnBackPressedCallback
    var step = 1

    lateinit var mainContext: Context
    lateinit var binding: FragmentLetterBinding

    lateinit var menuRVAdapter: LetterMenuRVAdapter

    lateinit var viewData: ArrayList<Int>
    lateinit var menuData: ArrayList<String>

    lateinit var imgURI: Uri
    lateinit var imgPath: String

    val gal =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            imgURI = it.data?.data!!
            Log.d("imgUri", imgURI.toString())
        }

    val cam = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.d("imgUri", imgURI.toString())
    }

    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val PERMISSIONS_REQUEST = 100


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLetterBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission(PERMISSIONS, PERMISSIONS_REQUEST)

        initListener()

        initRv()

    }

    override fun onResume() {
        super.onResume()

        MainFragmentManager.viewWidth = binding.clLetterTab.rootView.width
    }


    fun initListener() {
//        binding.ivLetterClose.setOnClickListener {
//            MainFragmentManager.letterClose(this@LetterFragment)
//        }

        binding.fabLetterAdd.setOnClickListener {
            binding.fabLetterAdd.hide()
            binding.fabLetterSend.hide()

            binding.tvLetterAddBackground.visibility = View.VISIBLE
            binding.rvLetterAddMenu.visibility = View.VISIBLE
        }

        binding.tvLetterAddBackground.setOnClickListener {
            binding.tvLetterAddBackground.visibility = View.GONE
            binding.rvLetterAddMenu.visibility = View.GONE

            binding.fabLetterSend.show()
            binding.fabLetterAdd.show()
        }

        binding.fabLetterSend.setOnClickListener {

        }
    }

    private fun initRv() {
        viewData = ArrayList()
        viewData.add(0)

        menuData = ArrayList<String>()
        menuData.apply {
            add("카메라")
            add("앨범")
            add("녹음")
        }

        menuRVAdapter = LetterMenuRVAdapter(menuData)
        menuRVAdapter.onlistener = object : LetterMenuRVAdapter.OnListener {
            override fun onClick(pos: Int) {
                binding.tvLetterAddBackground.visibility = View.GONE
                binding.rvLetterAddMenu.visibility = View.GONE

                binding.fabLetterSend.show()
                binding.fabLetterAdd.show()

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


        binding.rvLetterAddMenu.adapter = menuRVAdapter

        val objectAdapter = LetterObjectRVAdapter()
        objectAdapter.clistener = object: LetterObjectRVAdapter.Clistener {
            override fun onClick(title: String) {
                MainFragmentManager.objectOpen(title)
            }

        }
        binding.rvLetterObject.adapter = objectAdapter
        val objectLayoutManager = FlexboxLayoutManager(context)
        objectLayoutManager.alignItems = AlignItems.FLEX_START
        binding.rvLetterObject.layoutManager = objectLayoutManager


    }


    fun openRecord() {

    }

    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val sdf = java.text.SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis()) + ".jpg"
        //val photoFile = createTmpFile()!!
        val photoFile = File(
            File(mainContext.filesDir.toString() + "/image").apply{
                if (!this.exists()) {
                    this.mkdirs()
                }
            },
            filename
        )
        imgURI = FileProvider.getUriForFile(
            mainContext, "com.inha.hbc.fileprovider", photoFile
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

    fun checkPermission(permissions: Array<String>, flag: Int):Boolean {
        val unPremList = ArrayList<String>()
        for (i in permissions) {
            if (mainContext.checkSelfPermission(i) != PackageManager.PERMISSION_GRANTED) {
                unPremList.add(i)
            }
        }
        if (unPremList.isNotEmpty()) {
            requireActivity().requestPermissions(unPremList.toTypedArray(), PERMISSIONS_REQUEST)
            return false
        }
        return true
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.tvLetterAddBackground.visibility == View.VISIBLE) {
                    binding.tvLetterAddBackground.visibility = View.GONE
                    binding.rvLetterAddMenu.visibility = View.GONE

                    binding.fabLetterSend.show()
                    binding.fabLetterAdd.show()
                }

                else if (step == 3) {
                    step = 2

                }
                else if (step == 2) {
                    step = 1
                }
                else {
                    MainFragmentManager.letterClose(this@LetterFragment)
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