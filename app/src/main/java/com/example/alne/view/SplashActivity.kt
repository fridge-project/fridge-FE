package com.example.alne.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.alne.MainActivity
import com.example.alne.databinding.ActivitySplashBinding
import com.example.flo.Network.AuthApi
import com.example.flo.Network.AuthResponse
import com.example.flo.Network.getRetrofit
import com.example.flo.model.User
//import com.kakao.sdk.auth.AuthApiClient
//import com.kakao.sdk.common.model.KakaoSdkError
//import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)

        var handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
//            checkKakaoLogin()
        }
        ,1000)
    }

//    fun checkKakaoLogin(){
//        UserApiClient.instance.loginWithKakaoAccount(this@SplashActivity){ token, error ->
//            if (error != null) {
//                Log.d("loginWithKakaoAccount", "로그인 실패", error)
//            }
//            else if (token != null) {
//                Log.d("loginWithKakaoAccount", "로그인 성공 ${token.accessToken}")
//            }
//        }
//        if(AuthApiClient.instance.hasToken()){
//            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                Log.d("token", tokenInfo.toString())
//                if (error != null) {
//                    if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
//                        //로그인 필요
//
//                        startActivity(Intent(this, StartActivity::class.java))
//                    }
//                    else {
//                        //기타 에러
//                    }
//                }else {
//                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
//                    startActivity(Intent(this, MainActivity::class.java))
//                }
//            }
//        }else {
//            //로그인 필요
//            if(autoLogin()){
//                startActivity(Intent(this, MainActivity::class.java))
//            }else{
//                startActivity(Intent(this, StartActivity::class.java))
//            }
//        }
//    }

    fun autoLogin(): Boolean{
        val sharedPreferences = getSharedPreferences("login_setting", MODE_PRIVATE)
        return sharedPreferences.getBoolean("autoLogin", false)
    }

}