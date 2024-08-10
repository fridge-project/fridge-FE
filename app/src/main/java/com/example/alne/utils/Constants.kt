package com.example.alne.utils

import android.content.res.Resources

enum class RESPONSE_STATUS{
    OKAY,
    FAIL,
    NETWORK_ERROR
}

const val BASE_URL = "http://10.0.2.2:3000"

enum class Recipe_TYPE {
    DEFAULT,
    CATEGORY1,
    CATEGORY2,
    SEARCH
}

fun Float.fromDpToPx(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()