package com.inha.hbc.util.fragmentmanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.inha.hbc.data.local.SignupData
import com.inha.hbc.ui.login.ui.*

object NormLoginFragmentManager {
    lateinit var fragmentManager: FragmentManager
    var frameId = 0
    lateinit var activity: LoginActivity
    lateinit var baseLoginFragment: NormalLoginFragment

    var forgetArr = ArrayList<Fragment>()
    lateinit var data: SignupData
    var currentPage = 0
    var isId = false

    fun setManager(manager: FragmentManager, id: Int, act: LoginActivity) {
        fragmentManager = manager
        frameId = id
        activity = act
    }


    fun normloginStart() {
        baseLoginFragment = NormalLoginFragment()
        fragmentManager.beginTransaction().add(frameId, baseLoginFragment).commit()
    }

    fun idStart() {
        fragmentManager.beginTransaction().hide(baseLoginFragment).commit()

        forgetArr.apply {
            add(Forget1Fragment())
            add(Forget2Fragment())
            add(ForgetIdFragment())
        }
        for (i in 0..2) {
            fragmentManager.beginTransaction().add(frameId, forgetArr[i]).commit()
            fragmentManager.beginTransaction().hide(forgetArr[i]).commit()
        }


        fragmentManager.beginTransaction().show(forgetArr[0]).commit()
        data = SignupData()
        currentPage = 1
        isId = true
    }

    fun pwstart() {
        forgetArr.apply {
            add(ForgetPw1Fragment())
            add(Forget1Fragment())
            add(Forget2Fragment())
            add(ForgetPw2Fragment())
            add(ForgetPw3Fragment())
        }

        for (i in 0..4) {
            fragmentManager.beginTransaction().add(frameId, forgetArr[i]).commit()
            fragmentManager.beginTransaction().hide(forgetArr[i]).commit()
        }

        fragmentManager.beginTransaction().hide(baseLoginFragment).commit()

        fragmentManager.beginTransaction().show(forgetArr[0]).commit()
        data = SignupData()
        currentPage = 1
        isId = false
    }

    fun end() {
        activity.hideKeyboard()
        fragmentManager.beginTransaction().show(baseLoginFragment).commit()
        fragmentManager.beginTransaction().replace(frameId, baseLoginFragment).commit()
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        data = SignupData()
        currentPage = 0
        forgetArr.clear()
    }

    fun transaction(start: Int, dest: Int) {
        fragmentManager.beginTransaction().hide(forgetArr[start - 1]).commit()
        fragmentManager.beginTransaction().show(forgetArr[dest - 1]).commit()
        activity.hideKeyboard()
        currentPage = dest
    }

    fun forgetBackPressed() {
        if (currentPage > 1) {
            fragmentManager.beginTransaction().hide(forgetArr[currentPage - 1]).commit()
            fragmentManager.beginTransaction().show(forgetArr[currentPage - 2]).commit()
            activity.hideKeyboard()
            currentPage--
        }
        else if (currentPage == 1){
            end()
        }

        else {
            baseBackPressed()
        }
    }

    fun baseBackPressed() {
        fragmentManager.beginTransaction().replace(frameId, LoginFragment()).commit()
    }
}