package com.inha.hbc.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.inha.hbc.R
import com.inha.hbc.databinding.DialogLetterBinding

class LetterDialog: DialogFragment() {
    lateinit var binding: DialogLetterBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogLetterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.popup_width)
        val height = resources.getDimensionPixelSize(R.dimen.popup_height)
        dialog?.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

    }

    fun initListener() {
        binding.tvDialogLetterCancel.setOnClickListener {
            dismiss()
        }

        binding.tvDialogLetterOk.setOnClickListener {

        }
    }
}