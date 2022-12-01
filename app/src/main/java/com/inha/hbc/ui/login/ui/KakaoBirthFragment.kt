package com.inha.hbc.ui.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.req.CheckBirthData
import com.inha.hbc.data.remote.resp.GetMyInfoSuccess
import com.inha.hbc.data.remote.resp.message.RoomInfoSuccess
import com.inha.hbc.databinding.FragmentKakaoBirthBinding
import com.inha.hbc.ui.assist.cakeSelectionAssist
import com.inha.hbc.ui.login.view.CheckBirthView
import com.inha.hbc.ui.login.view.RefreshFcmView
import com.inha.hbc.ui.main.ui.MainActivity
import com.inha.hbc.ui.main.view.RoomInfoView
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.network.RetrofitService
import com.inha.hbc.util.network.message.MessageRetrofitService
import com.inha.hbc.util.sharedpreference.GlobalApplication
import java.util.Calendar

class KakaoBirthFragment(val myInfo: GetMyInfoSuccess): Fragment(), CheckBirthView, RefreshFcmView, RoomInfoView {
    lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentKakaoBirthBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKakaoBirthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding.ivKakaoBirthBack.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(NormLoginFragmentManager.frameId, LoginFragment(true)).commit()
        }

        binding.npKakaoBirthYear.apply {
            val cal = Calendar.getInstance()
            val maxYear = cal.get(Calendar.YEAR) - 1
            minValue = 1900
            maxValue = maxYear
            value = maxValue - 20
        }

        binding.npKakaoBirthMonth.apply {
            minValue = 1
            maxValue = 12
        }

        binding.npKakaoBirthDay.apply {
            minValue = 1
            maxValue = setDay(binding.npKakaoBirthYear.value, binding.npKakaoBirthMonth.value)
            value = 1
        }

        binding.npKakaoBirthMonth.setOnValueChangedListener { numberPicker, i, i2 ->
            val days = setDay(binding.npKakaoBirthYear.value, i2)
            binding.npKakaoBirthDay.maxValue = days
        }

        binding.tvKakaoBirthNext.setOnClickListener {
            var data = CheckBirthData(binding.npKakaoBirthDay.value, binding.npKakaoBirthMonth.value,
                "SOLAR", binding.npKakaoBirthYear.value)
            RetrofitService().checkBirth(data, this)

        }
    }

    fun setDay(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.get(Calendar.YEAR)
        cal.set(year, month - 1, 1)
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    override fun onBirthSuccess() {
        MessageRetrofitService().roomInfo(GlobalApplication.prefs.getInfo()!!.id.toString(), this)
    }

    override fun onBirthFailure() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager.beginTransaction().replace(NormLoginFragmentManager.frameId, LoginFragment(true)).commit()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    override fun onRefreshFcmSuccess() {
    }

    override fun onRefreshFcmFailure() {
    }

    override fun onRoomInfoSuccess(resp: RoomInfoSuccess) {

        val parArr = arrayListOf(resp)
        val infArr = arrayListOf(myInfo)
        RetrofitService().refreshFcm(GlobalApplication.prefs.getFcmtoken()!!)
        val intent = Intent(requireActivity(), MainActivity::class.java).apply {
            putParcelableArrayListExtra("data", parArr)
            putParcelableArrayListExtra("info", infArr)
        }
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onRoomInfoFailure() {
        TODO("Not yet implemented")
    }

}