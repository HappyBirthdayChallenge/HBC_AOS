package com.inha.hbc.util.fragmentmanager

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.ui.login.ui.*

object SignupFragmentManager {
    lateinit var fragmentManager: FragmentManager
    var frameId = 0
    lateinit var activity: LoginActivity


    var signupArr = ArrayList<Fragment>()
    lateinit var signupData : SignupData
    var signupCurrentPage = 0

    fun setManager(manager: FragmentManager, id: Int, act: LoginActivity) {
        fragmentManager = manager
        frameId = id
        activity = act
    }

    fun start() {
        signupArr.apply {
            add(Signup1Fragment())
            add(Signup2Fragment())
            add(Signup3Fragment())
            add(Signup4Fragment())
            add(Signup5Fragment())
            add(Signup6Fragment())
        }

        for (i in 0..5) {
            fragmentManager.beginTransaction().add(frameId, signupArr[i]).commit()
            fragmentManager.beginTransaction().hide(signupArr[i]).commit()
            signupCurrentPage = 1
        }

        fragmentManager.beginTransaction().show(signupArr[0]).commit()
        signupData = SignupData()
    }

    fun end() {
        activity.hideKeyboard()
        fragmentManager.beginTransaction().replace(frameId, LoginFragment(false)).commit()
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        signupData = SignupData()
        signupCurrentPage = 0

        signupArr.clear()
    }


    fun transaction(start: Int, dest: Int) {
        fragmentManager.beginTransaction().hide(signupArr[start - 1]).commit()
        fragmentManager.beginTransaction().show(signupArr[dest - 1]).commit()
        activity.hideKeyboard()
        signupCurrentPage = dest
    }

    fun backPressed() {
        if (signupCurrentPage > 1) {
            if (signupCurrentPage == 5) {
                popupAuth()
                return
            }
            fragmentManager.beginTransaction().hide(signupArr[signupCurrentPage - 1]).commit()
            fragmentManager.beginTransaction().show(signupArr[signupCurrentPage - 2]).commit()
            activity.hideKeyboard()
            signupCurrentPage--
        }
        else {
            end()
        }

    }

    fun popupAuth() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("사용자 인증을 취소하시겠어요?")
            .setMessage("사용자 인증을 취소하시면 전화번호 인증부터 다시 진행하게 됩니다.")
            .setPositiveButton("네, 취소할게요",
                DialogInterface.OnClickListener { dialog, id ->
                    transaction(5, 4)
                })
            .setNegativeButton("아니요",
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss()
                })
        builder.show()
    }
}