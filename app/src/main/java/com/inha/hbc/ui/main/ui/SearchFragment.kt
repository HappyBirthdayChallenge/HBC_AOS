package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.resp.main.GlobalSearchSuccess
import com.inha.hbc.databinding.FragmentSearchBinding
import com.inha.hbc.ui.main.view.GlobalSearchView
import com.inha.hbc.util.network.main.MainRetrofitService
import kotlin.concurrent.timer

class SearchFragment: Fragment(), GlobalSearchView {
    lateinit var binding: FragmentSearchBinding
    var isTyping = false
    var time = 0
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
        timer(period = 1) {
            time++
            if (!isTyping && time == 100) {
                MainRetrofitService().globalSearch(binding.tieSearch.text.toString(), this@SearchFragment)
                time = 0
                this.cancel()
            }
            if (isTyping || time == 100) {
                time = 0
                this.cancel()
            }
        }
    }

    override fun onGlobalSearchSuccess(resp: GlobalSearchSuccess) {
        resp.
    }

    override fun onGlobalSearchFailure() {
        TODO("Not yet implemented")
    }
}