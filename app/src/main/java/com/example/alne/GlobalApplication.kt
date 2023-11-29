package com.example.alne

import android.app.Application

class GlobalApplication():Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
//        KakaoSdk.init(this, "b3be3ef965add985a1b3f7aa438b633c")
    }
}