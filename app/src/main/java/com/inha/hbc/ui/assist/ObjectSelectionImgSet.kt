package com.inha.hbc.ui.assist

import com.inha.hbc.R

fun selectionAssist(type: Int, pos: Int): Int {
    when(type) {
        0 -> {
            return when(pos) {
                0 -> R.drawable.img_deco_gift_1
                1 -> R.drawable.img_deco_gift_2
                2 -> R.drawable.img_deco_gift_3
                3 -> R.drawable.img_deco_gift_4
                4 -> R.drawable.img_deco_gift_5
                5 -> R.drawable.img_deco_gift_6
                6 -> R.drawable.img_deco_gift_7
                7 -> R.drawable.img_deco_gift_8
                8 -> R.drawable.img_deco_gift_9
                else -> R.drawable.img_deco_gift_10
            }
        }
        1 -> {
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
        4 -> {
            return when(pos) {
                0 -> R.drawable.img_deco_food_1
                1 -> R.drawable.img_deco_food_2
                2 -> R.drawable.img_deco_food_3
                3 -> R.drawable.img_deco_food_4
                4 -> R.drawable.img_deco_food_5
                5 -> R.drawable.img_deco_food_6
                6 -> R.drawable.img_deco_food_7
                7 -> R.drawable.img_deco_food_8
                8 -> R.drawable.img_deco_food_9
                else -> R.drawable.img_deco_food_10
            }
        }
        else-> return 1
    }

}

fun selectionCount(type: Int): Int {
    return when(type) {
        0 -> 10//선물
        1 -> 20//음료
        2 -> 10//장난감
        else -> 10//음식(4)
    }
}