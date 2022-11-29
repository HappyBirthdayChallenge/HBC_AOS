package com.inha.hbc.ui.assist

import com.inha.hbc.R


fun selectionCandle(num: Int, birth: Boolean): Int {
    when(num) {
        0 -> {
            return if (birth) R.drawable.img_deco_candle_fire_0
            else R.drawable.img_deco_candle_0
        }
        1 -> {
            return if (birth) R.drawable.img_deco_candle_fire_1
            else R.drawable.img_deco_candle_1
        }
        2 -> {
            return if (birth) R.drawable.img_deco_candle_fire_2
            else R.drawable.img_deco_candle_2
        }
        3 -> {
            return if (birth) R.drawable.img_deco_candle_fire_3
            else R.drawable.img_deco_candle_3
        }
        4 -> {
            return if (birth) R.drawable.img_deco_candle_fire_4
            else R.drawable.img_deco_candle_4
        }
        5 -> {
            return if (birth) R.drawable.img_deco_candle_fire_5
            else R.drawable.img_deco_candle_5
        }
        6 -> {
            return if (birth) R.drawable.img_deco_candle_fire_6
            else R.drawable.img_deco_candle_6
        }
        7 -> {
            return if (birth) R.drawable.img_deco_candle_fire_7
            else R.drawable.img_deco_candle_7
        }
        8 -> {
            return if (birth) R.drawable.img_deco_candle_fire_8
            else R.drawable.img_deco_candle_8
        }
        else -> {
            return if (birth) R.drawable.img_deco_candle_fire_9
            else R.drawable.img_deco_candle_9
        }
    }
}