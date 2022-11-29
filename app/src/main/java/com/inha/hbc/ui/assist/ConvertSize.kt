package com.inha.hbc.ui.assist

import com.inha.hbc.util.fragmentmanager.MainFragmentManager


fun dpToPx(int: Int): Int {
    val density = MainFragmentManager.baseActivity.resources.displayMetrics.density
    return (int * density).toInt()
}


fun pxtoDp(int: Int): Int {
    val density = MainFragmentManager.baseActivity.resources.displayMetrics.density
    return (int / density).toInt()
}