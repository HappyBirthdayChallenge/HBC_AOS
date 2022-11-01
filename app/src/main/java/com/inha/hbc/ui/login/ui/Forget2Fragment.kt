package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.data.remote.req.CheckCodeData
import com.inha.hbc.data.remote.req.FindIdData
import com.inha.hbc.data.remote.resp.CodeFailure
import com.inha.hbc.data.remote.resp.CodeSuccess
import com.inha.hbc.data.remote.resp.FindIdSuccess
import com.inha.hbc.databinding.FragementForget2Binding
import com.inha.hbc.ui.login.view.CheckCodeView
import com.inha.hbc.ui.login.view.FindIdView
import com.inha.hbc.ui.login.view.SendCodeView
import com.inha.hbc.util.RetrofitService
import java.util.*
import kotlin.concurrent.timer

class Forget2Fragment: Fragment(), CheckCodeView, FindIdView, SendCodeView {
    var time = 0
    lateinit var timer: Timer
    var auth = ""
    lateinit var data: SignupData
    lateinit var binding: FragementForget2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragementForget2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : Forget2FragmentArgs by navArgs()
        data = args.forgetData

        startTimer()
        initListener()
    }

    fun initListener() {
        binding.tvForget2Next.setOnClickListener {
            binding.lavForget2Loading.visibility = View.VISIBLE
            auth = binding.tieForget2PhoneAuth.text.toString()

            val reqData = CheckCodeData(auth.toInt(),  data.phone!!, "SIGNUP")
            RetrofitService().checkCode(reqData, this)
        }

        binding.tvForget2Resend.setOnClickListener {
            binding.lavForget2Loading.visibility = View.VISIBLE
            RetrofitService().reqCode(data.phone!!, this)
        }
    }

    fun startTimer() {
        timer = timer(period = 1000) {
            time++

            if (time == 180) {
                this.cancel()

                requireActivity().runOnUiThread {

                    binding.tieForget2PhoneAuth.isEnabled = false
                    binding.tvForget2Next.isEnabled = false

                    binding.tvForget2Error.text = "인증시간이 초과되었습니다. 코드 재발송을 하시거나\n이전 화면으로 돌아가서 다시 시도해주세요"

                }
            }
            val sec = time

            val left = 180 - sec
            val m = left/60
            val s: String= if (left%60 >= 10) (left%60).toString()
            else "0" + (left%60).toString()

            activity?.runOnUiThread{
                binding.tvForget2PhoneTime.text = "$m:$s"
            }
        }
    }
    override fun onCheckCodeResponseSuccess(respData: CodeSuccess) {

        data.key = respData.data.key
        if (data.id.isNullOrEmpty()) {
            val reqData = FindIdData(data.key!!, data.name!!, data.phone!!)
            RetrofitService().findId(reqData, this)
        }
        else {
            val action = Forget2FragmentDirections.actionLoginForget2ToLoginForgetPw2(data)
            binding.lavForget2Loading.visibility = View.GONE

            findNavController().navigate(action)
        }
    }

    override fun onCheckCodeResponseFailure(respData: CodeSuccess) {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = respData.message
    }

    override fun onCheckCodeResponseFailure(respData: CodeFailure) {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = respData.message+ respData.errors!!.reason
    }

    override fun onCheckCodeResponseFailure() {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = "서버 연결 오류"
    }

    override fun onFindIdSuccess(respData: FindIdSuccess) {
        data.id = respData.data!!.username
        val action = Forget2FragmentDirections.actionLoginForget2ToLoginForgetId(data)
        binding.lavForget2Loading.visibility = View.GONE
        findNavController().navigate(action)
    }

    override fun onFindIdFailure() {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = "에러"
    }

    override fun onSendCodeSuccess() {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = ""

        timer.cancel()
        time = 0
        binding.tvForget2PhoneTime.text = "3:00"
        startTimer()
    }

    override fun onSendCodeFailure() {
    }


}