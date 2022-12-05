package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.databinding.FragmentSearchBinding
import kotlin.concurrent.timer

class SearchFragment: Fragment() {
    lateinit var binding: FragmentSearchBinding
    var isTyping = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {

    }

    fun initListener() {
        binding.tieSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isTyping = true
            }

            override fun afterTextChanged(p0: Editable?) {
                isTyping = false
                startTimer()

            }

        })
    }

    fun startTimer() {
        timer(period = 100) {
            if (!isTyping) {

            }
        }
    }
}