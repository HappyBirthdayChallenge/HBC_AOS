package com.inha.hbc.ui.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.data.remote.req.BirthDate
import com.inha.hbc.data.remote.req.CheckBirthData
import com.inha.hbc.databinding.FragmentKakaoBirthBinding
import com.inha.hbc.ui.login.view.CheckBirthView
import com.inha.hbc.ui.main.MainActivity
import com.inha.hbc.util.RetrofitService
import java.util.Calendar

class KakaoBirthFragment: Fragment(), CheckBirthView {
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
            findNavController().popBackStack()
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
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }

    override fun onBirthFailure() {
    }

}