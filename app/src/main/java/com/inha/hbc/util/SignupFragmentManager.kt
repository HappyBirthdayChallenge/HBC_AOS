package com.inha.hbc.util

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
        fragmentManager.beginTransaction().replace(frameId, LoginFragment()).commit()
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
            fragmentManager.beginTransaction().hide(signupArr[signupCurrentPage - 1]).commit()
            fragmentManager.beginTransaction().show(signupArr[signupCurrentPage - 2]).commit()
            activity.hideKeyboard()
            signupCurrentPage--
        }
        else {
            end()
        }

    }

}