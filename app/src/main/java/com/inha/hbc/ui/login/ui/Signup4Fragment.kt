package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.databinding.FragmentSignup4Binding
import com.inha.hbc.ui.login.view.CheckCodeView
import com.inha.hbc.ui.login.view.CheckPhoneView
import com.inha.hbc.ui.login.view.SendCodeView
import com.inha.hbc.util.RetrofitService

class Signup4Fragment: Fragment(), CheckPhoneView, CheckCodeView, SendCodeView {
    var step = false
    var phone = ""
    lateinit var binding: FragmentSignup4Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignup4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.tieSignup4Phone.addTextChangedListener { PhoneNumberFormattingTextWatcher() }
        binding.ivSignup4Back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvSignup4Next.setOnClickListener {
            if (step) {
                phone = binding.tieSignup4Phone.text.toString()
                if (phone.isNotEmpty()) {
                    binding.lavSignup4Loading.visibility = View.VISIBLE
                    RetrofitService().checkPhone(phone, this)
                }
            }
            else {
                val code = binding.tieSignup4PhoneAuth.text.toString()
                if(code.length == 6) {
                    binding.lavSignup4Loading.visibility = View.VISIBLE
                    RetrofitService()
                }
            }
        }
    }

    override fun onResponseSuccess() {
        RetrofitService().reqCode(phone, this)
    }

    override fun onResponseFailure() {
    }

    override fun onCheckCodeResponseSuccess() {
        binding.lavSignup4Loading.visibility = View.GONE
        val args: Signup4FragmentArgs by navArgs()
        var data = args.userData
        data.phone = phone
        val action = Signup4FragmentDirections.actionLoginSignup4ToLoginSignup5(data)
        findNavController().navigate(action)
    }

    override fun onCheckCodeResponseFailure() {
        TODO("Not yet implemented")
    }

    override fun onSendCodeSuccess() {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Description.visibility = View.VISIBLE
        binding.tilSignup4PhoneAuth.visibility = View.VISIBLE
        binding.tvSignup4PhoneTime.visibility = View.VISIBLE

        binding.tieSignup4Phone.isEnabled = false
        step = true
    }

    override fun onSendCodeFailure() {
        TODO("Not yet implemented")
    }
}