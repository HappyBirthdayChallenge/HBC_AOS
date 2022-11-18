package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import com.inha.hbc.util.fragmentmanager.NormLoginFragmentManager
import com.inha.hbc.util.network.RetrofitService
import java.util.*
import kotlin.concurrent.timer

class Forget2Fragment: Fragment(), CheckCodeView, FindIdView, SendCodeView {
    var time = 0
    var timer: Timer? = null
    var auth = ""
    lateinit var data: SignupData
    lateinit var binding: FragementForget2Binding
    var resend = false
    var id = true
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
        data = NormLoginFragmentManager.data


        initListener()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        binding.tvForget2Error.text = ""
        if (hidden) {
            if (timer != null) {
                timer!!.cancel()
            }
            binding.tieForget2PhoneAuth.setText("")
            if (id) {
                data.id = null
            }
            time = 0
        }
        else {
            id = data.id.isNullOrEmpty()
            startTimer()
        }
    }

    fun initListener() {
        binding.ivForget2Back.setOnClickListener {
            NormLoginFragmentManager.forgetBackPressed()
        }
        binding.tvForget2Next.setOnClickListener {
            binding.lavForget2Loading.visibility = View.VISIBLE
            auth = binding.tieForget2PhoneAuth.text.toString()
            if (!auth.isNullOrEmpty()) {
                val reqData = if (id) {
                    CheckCodeData(auth, data.phone!!, "FIND_ID")
                }
                else {
                    CheckCodeData(auth, data.phone!!, "FIND_PW")
                }
                    RetrofitService().checkCode(reqData, this)
                }
            else {
                binding.tvForget2Error.text = "인증 코드를 잘못 입력했어요"
                binding.lavForget2Loading.visibility = View.GONE
            }

        }

        binding.tvForget2Resend.setOnClickListener {
            binding.lavForget2Loading.visibility = View.VISIBLE
            resend = true
            if (id) {
                id = true
                RetrofitService().reqCode(data.phone!!, this, "FIND_ID")
            }
            else {
                id = false
                RetrofitService().reqCode(data.phone!!, this, "FIND_PW")
            }
        }
        binding.tieForget2PhoneAuth.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                auth = binding.tieForget2PhoneAuth.text.toString()
                if (auth.length == 6) {
                    binding.tvForget2Error.text = ""
                }
                else {
                    binding.tvForget2Error.text = "인증 코드를 잘못 입력했어요"
                }
            }
        })
    }


    fun startTimer() {
        timer = timer(period = 1000) {
            time++

            if (time == 180) {
                this.cancel()

                NormLoginFragmentManager.activity.runOnUiThread {

                    binding.tieForget2PhoneAuth.isEnabled = false
                    binding.tvForget2Next.isEnabled = false
                    binding.tvForget2PhoneTime.visibility = View.GONE

                    time = 0
                    binding.tvForget2Error.text = "입력 시간이 초과 되었어요. 다시 시도해 주세요."

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

        NormLoginFragmentManager.data.key = respData.data.key
        if (id) {
            val reqData = FindIdData(data.key!!, data.name!!, data.phone!!)
            RetrofitService().findId(reqData, this)
        }
        else {
            binding.lavForget2Loading.visibility = View.GONE


            NormLoginFragmentManager.transaction(3, 4)
        }
    }

    override fun onCheckCodeResponseFailure(respData: CodeSuccess) {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = "인증 코드를 잘못 입력했어요"
    }

    override fun onCheckCodeResponseFailure(respData: CodeFailure) {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = "인증 코드를 잘못 입력했어요"
    }

    override fun onCheckCodeResponseFailure() {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = "서버 연결 오류"
    }

    override fun onFindIdSuccess(respData: FindIdSuccess) {
        NormLoginFragmentManager.data.id = respData.data!!.username
        binding.lavForget2Loading.visibility = View.GONE
        NormLoginFragmentManager.transaction(2, 3)
    }

    override fun onFindIdFailure() {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = "에러"
    }

    override fun onSendCodeSuccess() {
        binding.lavForget2Loading.visibility = View.GONE
        binding.tvForget2Error.text = ""
        binding.tvForget2PhoneTime.visibility = View.VISIBLE
        binding.tieForget2PhoneAuth.isEnabled = true
        binding.tvForget2Next.isEnabled = true
        binding.tieForget2PhoneAuth.setText("")


        if (resend) {
            timer!!.cancel()
            time = 0
            resend= false
        }
        binding.tvForget2PhoneTime.text = "3:00"
        startTimer()
    }

    override fun onSendCodeFailure() {
    }


}