package com.inha.hbc.ui.assist

fun convertDeco(name: String): String {
    val arr = name.split("_")
    var type = arr[arr.size - 2].uppercase()
    if (type == "toy") {
        type = "DOLL"
    }
    return type + "_TYPE" + arr[arr.size - 1]
}

fun convertAnime(name: String): String {
    val arr = name.split("_")
    var num = arr[arr.size - 1][0].toString()
    return "PARTY_POPPER_TYPE$num"
}