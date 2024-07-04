package com.example.alne

import android.app.Application
import android.util.Log
import com.example.alne.room.AppDatabase
import com.example.alne.utils.SharedPrefManager
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication():Application() {

    companion object {
        lateinit var prefManager: SharedPrefManager
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("GlobalApplication", "GlobalApplication_Create")
        // 다른 초기화 코드들
        prefManager = SharedPrefManager(applicationContext)
        appDatabase = AppDatabase.getInstance(applicationContext)!!
        // Kakao SDK 초기화
        KakaoSdk.init(this, "ee52fc7baab1b48b61bcf3e7f3b617f0")
    }
}