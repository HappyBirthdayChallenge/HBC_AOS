package com.inha.hbc.ui.assist

import com.inha.hbc.R

fun selectionAssist(title: String, pos: Int): Int {
    when(title) {
        "음료" -> {
            return when(pos) {
                0 -> R.drawable.img_deco_drink_1
                1 -> R.drawable.img_deco_drink_2
                2 -> R.drawable.img_deco_drink_3
                3 -> R.drawable.img_deco_drink_4
                4 -> R.drawable.img_deco_drink_5
                5 -> R.drawable.img_deco_drink_6
                6 -> R.drawable.img_deco_drink_7
                7 -> R.drawable.img_deco_drink_8
                8 -> R.drawable.img_deco_drink_9
                9 -> R.drawable.img_deco_drink_10
                10 -> R.drawable.img_deco_drink_11
                11 -> R.drawable.img_deco_drink_12
                12 -> R.drawable.img_deco_drink_13
                13 -> R.drawable.img_deco_drink_14
                14 -> R.drawable.img_deco_drink_15
                15 -> R.drawable.img_deco_drink_16
                16 -> R.drawable.img_deco_drink_17
                17 -> R.drawable.img_deco_drink_18
                18 -> R.drawable.img_deco_drink_19
                else -> R.drawable.img_deco_drink_20
            }
        }
        "음식" -> {
            return when(pos) {
                0 -> R.drawable.img_deco_food_1
                1 -> R.drawable.img_deco_drink_2
                2 -> R.drawable.img_deco_drink_3
                3 -> R.drawable.img_deco_drink_4
                4 -> R.drawable.img_deco_drink_5
                5 -> R.drawable.img_deco_drink_6
                6 -> R.drawable.img_deco_drink_7
                7 -> R.drawable.img_deco_drink_8
                8 -> R.drawable.img_deco_drink_9
                9 -> R.drawable.img_deco_drink_10
                10 -> R.drawable.img_deco_drink_11
                11 -> R.drawable.img_deco_drink_12
                12 -> R.drawable.img_deco_drink_13
                13 -> R.drawable.img_deco_drink_14
                14 -> R.drawable.img_deco_drink_15
                15 -> R.drawable.img_deco_drink_16
                16 -> R.drawable.img_deco_drink_17
                17 -> R.drawable.img_deco_drink_18
                18 -> R.drawable.img_deco_drink_19
                else -> R.drawable.img_deco_drink_20
            }
        }
        else-> return 1
    }

}

fun selectionCount(title: String): Int {
    return when(title) {
        "음료" -> 20
        "음식" -> 10
        else -> 10
    }
}