package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.R
import com.inha.hbc.data.remote.req.IsMeData
import com.inha.hbc.databinding.FragementForget1Binding
import com.inha.hbc.ui.login.view.IsMeView
import com.inha.hbc.ui.login.view.SendCodeView
import com.inha.hbc.util.RetrofitService

class Forget1Fragment: Fragment(), IsMeView, SendCodeView {
    lateinit var binding: FragementForget1Binding
    var name = ""
    var phone = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragementForget1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.tieForget1PwPhone.addTextChangedListener( PhoneNumberFormattingTextWatcher() )
        binding.tvForget1Next.setOnClickListener {
            name = binding.tieForget1Name.text.toString()
            phone = binding.tieForget1PwPhone.text.toString()

            val data = IsMeData(name, phone)

            RetrofitService().isMe(data, this)
        }
    }

    override fun onMeSuccess() {
        RetrofitService().reqCode(phone, this)
    }

    override fun onMeFailure() {
    }

    override fun onSendCodeSuccess() {
        val args: Forget1FragmentArgs by navArgs()
        val data = args.forgetData
        data.name = name
        data.phone = phone

        val action = Forget1FragmentDirections.actionLoginForget1ToLoginForget2(data)

        findNavController().navigate(action)
    }

    override fun onSendCodeFailure() {
        TODO("Not yet implemented")
    }
}