package com.inha.hbc.ui.main.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.resp.main.GlobalSearchSuccess
import com.inha.hbc.databinding.FragmentSearchBinding
import com.inha.hbc.ui.adapter.MainSearchRVAdapter
import com.inha.hbc.ui.main.view.GlobalSearchView
import com.inha.hbc.util.network.main.MainRetrofitService
import kotlin.concurrent.timer
import com.inha.hbc.data.remote.resp.main.Result
import com.inha.hbc.util.fragmentmanager.MainFragmentManager
import java.util.regex.Pattern

class SearchFragment: Fragment(), GlobalSearchView {
    lateinit var binding: FragmentSearchBinding
    lateinit var adapter: MainSearchRVAdapter
    var dataArr = listOf<Result>()
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
        initListener()
    }

    fun initView() {
        adapter = MainSearchRVAdapter(dataArr)
        adapter.setMainSearch = object :MainSearchRVAdapter.SetMainSearch{
            override fun onClick(pos: Int) {
                MainFragmentManager.searchToFriend(dataArr[pos]!!, this@SearchFragment)
            }
        }
        binding.rvSearch.adapter = adapter
    }

    fun updateView(resp: GlobalSearchSuccess) {
        dataArr = resp.data!!.result
        adapter.data = dataArr
        adapter.notifyDataSetChanged()
        binding.lavSearch.visibility = View.GONE
    }

    fun initListener() {
        binding.tieSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                isTyping = true
            }

            override fun afterTextChanged(p0: Editable?) {
                if (checkValid(binding.tieSearch.text.toString()) == 0) {
                    return
                }
                isTyping = false
                startTimer()
                binding.lavSearch.visibility = View.VISIBLE
            }

        })
    }


    fun startTimer() {
        timer(period = 1) {
            time++
            if (!isTyping && time == 500) {
                Log.d("keyword", binding.tieSearch.text.toString())
                MainRetrofitService().globalSearch(binding.tieSearch.text.toString(), this@SearchFragment)
                time = 0
                this.cancel()
            }
            if (isTyping || time == 500) {
                time = 0
                this.cancel()
            }
        }
    }

    fun checkValid(str: String): Int {
        val keywordPattern = "^[가-힣A-Za-z\\d]{1,20}\$"
        val pattern = Pattern.compile(keywordPattern)
        val matcher = pattern.matcher(str)
        return if (matcher.find()) 1
        else 0
    }

    override fun onGlobalSearchSuccess(resp: GlobalSearchSuccess) {
        updateView(resp)
    }

    override fun onGlobalSearchFailure() {
        TODO("Not yet implemented")
    }
}