package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.req.CheckCodeData
import com.inha.hbc.data.remote.resp.*
import com.inha.hbc.databinding.FragmentSignup4Binding
import com.inha.hbc.ui.login.view.CheckCodeView
import com.inha.hbc.ui.login.view.CheckPhoneView
import com.inha.hbc.ui.login.view.SendCodeView
import com.inha.hbc.util.SignupFragmentManager
import com.inha.hbc.util.RetrofitService
import java.util.*
import kotlin.concurrent.timer

class Signup4Fragment: Fragment(), CheckPhoneView, CheckCodeView, SendCodeView {
    var step = true
    var phone = ""
    var time = 0
    lateinit var timer: Timer
    var resend = false
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
        binding.tieSignup4Phone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        binding.ivSignup4Back.setOnClickListener {
            SignupFragmentManager.transaction(4, 3)
        }

        binding.tvSignup4Next.setOnClickListener {
            if (step) {
                phone = binding.tieSignup4Phone.text.toString()
                if (checkPhone(phone)) {
                    binding.lavSignup4Loading.visibility = View.VISIBLE
                    RetrofitService().checkPhone(phone, this)
                }
                else {
                    binding.tvSignup4Error.text = "올바른 휴대폰 번호가 아닙니다."
                }
            }
            else {
                val code = binding.tieSignup4PhoneAuth.text.toString()
                if(code.length == 6) {
                    binding.lavSignup4Loading.visibility = View.VISIBLE
                    val data = CheckCodeData(code.toInt(), phone, "SIGNUP")
                    RetrofitService().checkCode(data, this)
                }
            }
        }

        binding.tvSignup4Resend.setOnClickListener {
            resend = true
            binding.lavSignup4Loading.visibility = View.VISIBLE
            RetrofitService().checkPhone(phone, this)
        }
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
    fun startTimer() {
        timer = timer(period = 1000) {
            time++

            if (time == 180) {
                this.cancel()
                step = false

                SignupFragmentManager.activity.runOnUiThread {
                    binding.tvSignup4Description.visibility = View.GONE
                    binding.tilSignup4PhoneAuth.visibility = View.GONE
                    binding.tvSignup4PhoneTime.visibility = View.GONE
                    binding.tvSignup4Resend.visibility = View.GONE

                    binding.tieSignup4Phone.isEnabled = true

                    binding.tvSignup4Error.text = "인증시간이 초과되었습니다."

                }
            }
            val sec = time

            val left = 180 - sec
            val m = left/60
            val s: String= if (left%60 >= 10) (left%60).toString()
            else "0" + (left%60).toString()

             activity?.runOnUiThread{
                binding.tvSignup4PhoneTime.text = "$m:$s"
            }
        }
    }

    override fun onResponseSuccess() {
        RetrofitService().reqCode(phone, this, "SIGNUP")
    }

    override fun onResponseFailure(data: PhoneSuccess) {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Error.text = data.message
    }

    override fun onResponseFailure(data: PhoneFailure) {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Error.text = data.errors!!.reason
    }

    override fun onResponseFailure() {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Error.text= "서버 연결 오류"
    }

    override fun onCheckCodeResponseSuccess(respData: CodeSuccess) {
        binding.tvSignup4Error.text = ""

        binding.lavSignup4Loading.visibility = View.GONE
        SignupFragmentManager.signupData.phone = phone
        SignupFragmentManager.signupData.key = respData.data.key
        SignupFragmentManager.transaction(4, 5)
    }

    override fun onCheckCodeResponseFailure(respData: CodeSuccess) {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Error.text = respData.message
    }

    override fun onCheckCodeResponseFailure(respData: CodeFailure) {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Error.text = respData.message+ respData.errors!![0].reason
    }

    override fun onCheckCodeResponseFailure() {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Error.text = "서버 연결 오류"
    }

    override fun onSendCodeSuccess() {
        binding.lavSignup4Loading.visibility = View.GONE
        binding.tvSignup4Description.visibility = View.VISIBLE
        binding.tilSignup4PhoneAuth.visibility = View.VISIBLE
        binding.tvSignup4PhoneTime.visibility = View.VISIBLE
        binding.tvSignup4Resend.visibility = View.VISIBLE

        binding.tvSignup4Error.text = ""

        binding.tieSignup4Phone.isEnabled = false
        step = false

        if (resend) {
            timer.cancel()
            time = 0
            resend = false
        }
        binding.tvSignup4PhoneTime.text = "3:00"
        startTimer()
    }

    override fun onSendCodeFailure() {
    }


}