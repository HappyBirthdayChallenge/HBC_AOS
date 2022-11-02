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
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.data.remote.req.IsMeData
import com.inha.hbc.databinding.FragementForget1Binding
import com.inha.hbc.ui.login.view.IsMeView
import com.inha.hbc.ui.login.view.SendCodeView
import com.inha.hbc.util.RetrofitService
import java.util.regex.Pattern

class Forget1Fragment: Fragment(), IsMeView, SendCodeView {
    lateinit var binding: FragementForget1Binding
    var name = ""
    var phone = ""

    lateinit var data: SignupData
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
        val args: Forget1FragmentArgs by navArgs()
        data = args.forgetData

        initListener()
    }

    fun initListener() {
        binding.ivForget1Back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tieForget1PwPhone.addTextChangedListener( PhoneNumberFormattingTextWatcher() )
        binding.tvForget1Next.setOnClickListener {
            name = binding.tieForget1Name.text.toString()
            phone = binding.tieForget1PwPhone.text.toString()

            if (!checkValid(name)) {
                binding.tvForget1Error.text = "이름이 규칙에 맞지 않습니다."
                return@setOnClickListener
            }

            if (!checkPhone(phone)) {
                binding.tvForget1Error.text = "전화번호가 규칙에 맞지 않습니다."
                return@setOnClickListener
            }

            val data = IsMeData(name, phone)

            RetrofitService().isMe(data, this)
        }
    }

    fun checkValid(name: String):Boolean {
        val namePattern = "^[가-힣A-Za-z\\d]{2,20}\$"
        val pattern = Pattern.compile(namePattern)
        val matcher = pattern.matcher(name)
        return matcher.find()
    }

    fun checkPhone(phone: String): Boolean {
        val arr = phone.split("-")
        if (arr.size != 3) return false
        if (arr[0].length != 3) return false
        if (arr[1].length == 3 || arr[1].length == 4) {

        }
        else {
            return false
        }
        if (arr[2].length != 4) return false
        return true

    }

    override fun onMeSuccess() {
        if (data.id.isNullOrEmpty()) {
            RetrofitService().reqCode(phone, this, "FIND_ID")
        }
        else {
            RetrofitService().reqCode(phone, this, "FIND_PW")
        }
    }

    override fun onMeFailure() {
    }

    override fun onSendCodeSuccess() {
        data.name = name
        data.phone = phone

        val action = Forget1FragmentDirections.actionLoginForget1ToLoginForget2(data)

        findNavController().navigate(action)
    }

    override fun onSendCodeFailure() {
    }
}