package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.data.remote.req.CheckBirthData
import com.inha.hbc.databinding.FragmentSignup5Binding
import com.inha.hbc.ui.login.view.CheckBirthView
import com.inha.hbc.util.RetrofitService
import java.util.Calendar

class Signup5Fragment: Fragment() {
    lateinit var binding: FragmentSignup5Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup5Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding.ivSignup5Back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.npSignup5Year.apply {
            val cal = Calendar.getInstance()
            val maxYear = cal.get(Calendar.YEAR) - 1
            minValue = 1900
            maxValue = maxYear
            value = maxValue - 20
        }

        binding.npSignup5Month.apply {
            minValue = 1
            maxValue = 12
        }

        binding.npSignup5Day.apply {
            minValue = 1
            maxValue = setDay(binding.npSignup5Year.value, binding.npSignup5Month.value)
            value = 1
        }

        binding.npSignup5Month.setOnValueChangedListener { numberPicker, i, i2 ->
            val days = setDay(binding.npSignup5Year.value, i2)
            binding.npSignup5Day.maxValue = days
        }

        binding.tvSignup5Next.setOnClickListener {
            val info = CheckBirthData(binding.npSignup5Day.value, binding.npSignup5Month.value, "SOLAR", binding.npSignup5Year.value )
            val args : Signup5FragmentArgs by navArgs()
            var data = args.userData
            data.year = binding.npSignup5Year.value
            data.month = binding.npSignup5Month.value
            data.day = binding.npSignup5Day.value
            val action = Signup5FragmentDirections.actionLoginSignup5ToLoginSignup6(data)
            findNavController().navigate(action)

        }
    }

    fun setDay(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.get(Calendar.YEAR)
        cal.set(year, month - 1, 1)
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

}