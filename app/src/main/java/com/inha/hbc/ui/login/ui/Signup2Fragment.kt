package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentSignup2Binding
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import java.util.regex.Pattern

class Signup2Fragment: Fragment() {
    lateinit var binding: FragmentSignup2Binding
    var pw  = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    fun initListener() {
        binding.ivSignup2Back.setOnClickListener {
            SignupFragmentManager.transaction(2, 1)
        }

        binding.tvSignup2Next.setOnClickListener {

            val result = checkValid()
            if (result == 1) {
                SignupFragmentManager.signupData.pw = pw
                SignupFragmentManager.transaction(2, 3)
            }
            else if (result == 2) {
                binding.tvSignup2Error.text = "비밀번호는 10~20자의 영문 대/소문자, 숫자, 특수문자(`~!@#\$%^&*())를 조합하여 설정해 주세요."
            }
        }
    }

    fun checkValid():Int {
        pw = binding.tieSignup2Pw.text.toString()
        val pwConfirm = binding.tieSignup2PwConfirm.text.toString()
        if (pw != pwConfirm) {
            binding.tvSignup2Error.text = "비밀번호가 일치하지 않아요."
            return 0
        }
        val pwPattern = "^(?=.*[`~!@#$%^&*()])(?=.*[A-Za-z])(?=.*[0-9])[[`~!@#$%^&*()]A-Za-z[0-9]]{10,20}$"
        val pattern = Pattern.compile(pwPattern)
        val matcher = pattern.matcher(pw)
        return if (matcher.find()) 1
        else 2
    }

}