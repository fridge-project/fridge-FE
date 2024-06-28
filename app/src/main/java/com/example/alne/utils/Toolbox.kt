package com.example.alne.utils

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.alne.R

object ToolBox {
    /** 프로그레스바 메소드  */
    var progressView: ViewGroup? = null
    fun showProgressingView(activity: Activity) {
        progressView =
            activity.getLayoutInflater().inflate(R.layout.progress_layout, null) as ViewGroup
        val v: View = activity.findViewById<View>(android.R.id.content).getRootView()
        val viewGroup: ViewGroup = v as ViewGroup
        viewGroup.addView(progressView)
    }

    fun hideProgressingView(activity: Activity) {
        val v: View = activity.findViewById<View>(android.R.id.content).getRootView()
        val viewGroup: ViewGroup = v as ViewGroup
        viewGroup.removeView(progressView)
    }
}