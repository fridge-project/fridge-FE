package com.example.alne

import android.app.Application
import android.util.Log
import com.example.alne.data.room.AppDatabase
import com.example.alne.utils.SharedPrefManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication :Application() {

    companion object {
        lateinit var prefManager: SharedPrefManager
        lateinit var appDatabase: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        prefManager = SharedPrefManager(applicationContext)
        appDatabase = AppDatabase.getInstance(applicationContext)!!

        Log.d("c", prefManager.getfirstApp().toString())
    }
}