package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentSignup3Binding
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import java.util.regex.Pattern

class Signup3Fragment: Fragment() {
    lateinit var binding: FragmentSignup3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            binding.tvSignup3Error.text = ""
        }
    }

    fun initListener() {
        binding.ivSignup3Back.setOnClickListener {
            SignupFragmentManager.transaction(3, 2)
        }

        binding.tvSignup3Next.setOnClickListener {
            val name = binding.tieSignup3Name.text.toString()
            if (checkValid(name)) {
                SignupFragmentManager.signupData.name = name
                SignupFragmentManager.transaction(3, 4)
            }
            else {
                binding.tvSignup3Error.text = "이름은 2~20자의 한글, 영문 대/소문자, 숫자만 사용하여 설정해 주세요."
            }
        }

        binding.tieSignup3Name.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val name = binding.tieSignup3Name.text.toString()
                if (!checkValid(name)) {
                    binding.tvSignup3Error.text = "이름은 2~20자의 한글, 영문 대/소문자, 숫자만 사용하여 설정해 주세요."
                }
                else {
                    binding.tvSignup3Error.text = ""
                }
            }

        })
    }

    fun checkValid(name: String):Boolean {
        val namePattern = "^[가-힣A-Za-z\\d]{2,20}\$"
        val pattern = Pattern.compile(namePattern)
        val matcher = pattern.matcher(name)
        return matcher.find()
    }


}