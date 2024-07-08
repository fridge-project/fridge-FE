package com.example.alne.utils

import android.content.res.Resources

enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NETWORK_ERROR
}

enum class Recipe_TYPE {
    DEFAULT,
    CATEGORY1,
    CATEGORY2,
    SEARCH
}

fun Float.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()