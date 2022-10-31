package com.inha.hbc.ui.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.inha.hbc.data.remote.req.CheckBirthData
import com.inha.hbc.databinding.FragmentSetbirthBinding
import com.inha.hbc.ui.main.MainActivity

class SetbirthFragment:Fragment() {
    lateinit var binding: FragmentSetbirthBinding
    lateinit var parentContext: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetbirthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentContext = context
    }

    fun initListener() {
        binding.tvSetbirthSkip.setOnClickListener {
            val intent = Intent(parentContext, MainActivity::class.java)
            startActivity(intent)
        }

        binding.etSetbirthYear.onFocusChangeListener =
            OnFocusChangeListener{ view, b ->
                if (!b) {
                    val data = binding.etSetbirthYear.text.toString().toInt()
                    if (data < 0 || data > 9999) {
                        Toast.makeText(parentContext, "생일인 해를 다시 확인해주세요!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        binding.etSetbirthMonth.onFocusChangeListener =
            OnFocusChangeListener { p0: View?, p1: Boolean ->
                if (!p1) {
                    val data = binding.etSetbirthMonth.text.toString().toInt()
                    if (data < 0 || data > 12) {
                        Toast.makeText(parentContext, "생일인 달을 다시 확인해주세요!", Toast.LENGTH_LONG).show()
                    }
                }
            }
        binding.etSetbirthDay.onFocusChangeListener =
            OnFocusChangeListener{ view, b ->
                if (!b) {
                    if (binding.etSetbirthDay.text.toString() != "") {
                        val data = binding.etSetbirthDay.text.toString().toInt()
                        if (data < 0 || data > 32) {
                            Toast.makeText(parentContext, "생일인 날을 다시 확인해주세요!", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }

        binding.cbSetbirthLunar.setOnClickListener {
            if (binding.cbSetbirthLunar.isChecked) {
                //lunar
            }
            else {
                //solar
            }
        }

        binding.tvSetbirthStart.setOnClickListener {
            //생일 다시 한번 검증해보고 전송
            val data = CheckBirthData(
                binding.etSetbirthDay.text.toString().toInt(),
                binding.etSetbirthMonth.text.toString().toInt(),
                "SOLAR",
                binding.etSetbirthYear.text.toString().toInt()
            )

            Log.d("dataStart", data.toString())
            //RetrofitService().setBirth(data, this)
        }

    }

//    override fun onSetBirthSuccess(data: NormSigninBody) {
//        val intent = Intent(parentContext, MainActivity::class.java)
//        startActivity(intent)
//    }
//
//    override fun onSetBirthFailure(code: Int) {
//        Toast.makeText(parentContext, "$code 에러 발생", Toast.LENGTH_LONG).show()
//    }
}