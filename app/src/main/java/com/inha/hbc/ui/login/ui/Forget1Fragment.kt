package com.inha.hbc.ui.login.ui

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.req.IsMeData
import com.inha.hbc.databinding.FragementForget1Binding
import com.inha.hbc.ui.login.view.IsMeView
import com.inha.hbc.ui.login.view.SendCodeView
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.network.RetrofitService
import java.util.regex.Pattern

class Forget1Fragment: Fragment(), IsMeView, SendCodeView {
    lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragementForget1Binding
    var isId = true
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

        isId = NormLoginFragmentManager.isId
        initListener()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.tvForget1Error.text = ""
        binding.tieForget1Name.setText("")
        binding.tieForget1PwPhone.setText("")
    }

    fun initListener() {
        binding.ivForget1Back.setOnClickListener {
            NormLoginFragmentManager.forgetBackPressed()
        }

        binding.tieForget1PwPhone.addTextChangedListener(object: PhoneNumberFormattingTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                phone = binding.tieForget1PwPhone.text.toString()
                if (!checkPhone(phone)) {
                    binding.tvForget1Error.text = "????????? ????????? ???????????? ??????????????????."
                }
                else {
                    binding.tvForget1Error.text = ""
                }
            }
        })
        binding.tieForget1Name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                name = binding.tieForget1Name.text.toString()
                if (!checkValid(name)) {
                    binding.tvForget1Error.text = "????????? 2~10?????? ??????, ?????? ???/?????????, ????????? ???????????? ????????? ?????????."
                }
                else {
                    binding.tvForget1Error.text = ""
                }
            }
        })
        binding.tvForget1Next.setOnClickListener {
            name = binding.tieForget1Name.text.toString()
            phone = binding.tieForget1PwPhone.text.toString()

            if (!checkValid(name)) {
                binding.tvForget1Error.text = "????????? 2~10?????? ??????, ?????? ???/?????????, ????????? ???????????? ????????? ?????????."
                return@setOnClickListener
            }

            if (!checkPhone(phone)) {
                binding.tvForget1Error.text = "????????? ????????? ???????????? ??????????????????."
                return@setOnClickListener
            }

            val data = IsMeData(name, phone)

            RetrofitService().isMe(data, this)
        }
    }

    fun checkValid(name: String):Boolean {
        val namePattern = "^[???-???A-Za-z\\d]{2,10}\$"
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
        binding.tvForget1Error.text = ""
        if (NormLoginFragmentManager.data.id.isNullOrEmpty()) {
            RetrofitService().reqCode(phone, this, "FIND_ID")
        }
        else {
            RetrofitService().reqCode(phone, this, "FIND_PW")
        }
    }

    override fun onMeFailure() {
        binding.tvForget1Error.text = "???????????? ????????? ?????????."
    }

    override fun onSendCodeSuccess() {
        NormLoginFragmentManager.data.name = name
        NormLoginFragmentManager.data.phone = phone

        if (isId) {
            NormLoginFragmentManager.transaction(1, 2)
        }
        else {
            NormLoginFragmentManager.transaction(2, 3)
        }
    }

    override fun onSendCodeFailure() {
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NormLoginFragmentManager.forgetBackPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }


    override fun onDetach() {
        super.onDetach()
        callback.remove()

    }

}