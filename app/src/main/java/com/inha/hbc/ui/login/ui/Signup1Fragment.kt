package com.inha.hbc.ui.login.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.resp.CheckIdFailure
import com.inha.hbc.data.remote.resp.CheckIdSuccess
import com.inha.hbc.databinding.FragmentSignup1Binding
import com.inha.hbc.ui.login.view.CheckIdView
import com.inha.hbc.util.fragmentmanager.SignupFragmentManager
import com.inha.hbc.util.network.RetrofitService
import java.text.BreakIterator
import java.util.regex.Pattern

class Signup1Fragment: Fragment(), CheckIdView {
    lateinit var callback: OnBackPressedCallback
    lateinit var binding: FragmentSignup1Binding
    var id = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignup1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    fun initListener() {
        binding.ivSignup1Back.setOnClickListener {
            SignupFragmentManager.end()
        }
        binding.tvSignup1Next.setOnClickListener {
            id = binding.tieSignup1Id.text.toString()

            if (checkValid()) {
                binding.lavSignup1Loading.visibility = View.VISIBLE
                RetrofitService().checkId(id, this)
            }
            else {
                binding.tvSignup1Error.text = "아이디는 5~20자의 영문 대/소문자, 숫자만 사용하여 설정해 주세요."
            }
        }

        binding.tieSignup1Id.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                id = binding.tieSignup1Id.text.toString()
                if (!checkValid()) {
                    binding.tvSignup1Error.text = "아이디는 5~20자의 영문 대/소문자, 숫자만 사용하여 설정해 주세요."
                }
                else {
                    binding.tvSignup1Error.text = ""
                }
            }

        })
    }

    fun checkValid(): Boolean {
        val idPattern = "^[A-Za-z\\d]{5,20}\$"
        val pattern = Pattern.compile(idPattern)
        val matcher = pattern.matcher(id)
        return matcher.find()
    }

    override fun onResponseSuccess(resp: CheckIdSuccess) {
        id = binding.tieSignup1Id.text.toString()
        SignupFragmentManager.signupData.id = id

        binding.lavSignup1Loading.visibility = View.GONE
        binding.tvSignup1Error.text = ""
        SignupFragmentManager.transaction(1, 2)
    }

    override fun onResponseFailure(resp: CheckIdSuccess) {
        binding.lavSignup1Loading.visibility = View.GONE
        binding.tvSignup1Error.text = "사용 중인 아이디에요. 다른 아이디를 입력해주세요."
    }

    override fun onResponseFailure(resp: CheckIdFailure) {
    }

    override fun onResponseFailure(message: String) {
        binding.lavSignup1Loading.visibility = View.GONE
        binding.tvSignup1Error.text = "사용 중인 아이디에요. 다른 아이디를 입력해주세요."
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                SignupFragmentManager.backPressed()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

}