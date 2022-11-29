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

fun serverDecoToId(name: String): Int {
    val arr = name.split("_")
    var num = arr[1][arr[1].length - 1].toString().toInt()
    var type = arr[0]

    var typenum = when (type) {
        "DRINK" -> 1
        "GIFT" -> 0
        "DOLL" -> 2
        "PIC" -> 3
        else -> 4
    }

    return selectionAssist(typenum, num)
}

fun serverAnimeToName(name: String): String {
    val arr = name.split("_")
    var num = arr[2][arr[2].length - 1].toString().toInt()
    return "json_deco_anime_$num"
}
