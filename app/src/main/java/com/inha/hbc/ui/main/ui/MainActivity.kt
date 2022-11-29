package com.inha.hbc.ui.main.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inha.hbc.data.remote.resp.GetMyInfoSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.ActivityMainBinding
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.util.ArrayList


class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ext = intent.extras
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ext!!.getParcelableArrayList("data", RoomInfoSuccess::class.java)
        }
        else {
            ext!!.getParcelableArrayList<RoomInfoSuccess>("data") as ArrayList<RoomInfoSuccess>
        }
        val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ext!!.getParcelableArrayList("info", GetMyInfoSuccess::class.java)
        }
        else {
            ext!!.getParcelableArrayList<GetMyInfoSuccess>("info") as ArrayList<GetMyInfoSuccess>
        }

        MainFragmentManager.init(supportFragmentManager, binding.fcMain.id, data!![0], info!![0],this)
        MainFragmentManager.start()

    }
}