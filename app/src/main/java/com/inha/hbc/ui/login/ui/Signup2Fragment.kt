package com.inha.hbc.ui.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.inha.hbc.databinding.FragmentSignup2Binding
import com.inha.hbc.util.RetrofitService
import java.util.regex.Pattern

class Signup2Fragment: Fragment() {
    lateinit var binding: FragmentSignup2Binding

    val args: Signup2FragmentArgs by navArgs()
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
            findNavController().popBackStack()
        }

        binding.tvSignup2Next.setOnClickListener {
            if (checkValid()) {
                var data = args.userData
                data.pw = pw
                val action = Signup2FragmentDirections.actionLoginSignup2ToLoginSignup3(data)
                findNavController().navigate(action)
            }
        }
    }

    fun checkValid():Boolean {
        pw = binding.tieSignup2Pw.text.toString()
        val pwConfirm = binding.tieSignup2PwConfirm.text.toString()
        if (pw != pwConfirm) {
            binding.tvSignup2Error.text = "비밀번호가 다릅니다!"
            return false
        }
        val idPattern = "^(?=.*[`~!@#$%^&*()])(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{5,20}$"
        val pattern = Pattern.compile(idPattern)
        val matcher = pattern.matcher(pw)
        return matcher.find()
    }
}