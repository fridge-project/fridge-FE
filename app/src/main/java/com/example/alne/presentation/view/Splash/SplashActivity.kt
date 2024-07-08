package com.example.alne.view.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alne.GlobalApplication
import com.example.alne.MainActivity
import com.example.alne.databinding.ActivitySplashBinding
import com.example.alne.viewmodel.SplashViewModel
import com.example.alne.Network.getRetrofit
import com.example.alne.R
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import retrofit2.Retrofit

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        setTheme(R.style.Theme_ALNE_SplashScreen)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        viewModel.autoLoginLiveData.observe(this, Observer{
            var handler = Handler(Looper.getMainLooper())
            Log.d("autoLoginLiveData", "onCreate: $it")
            if(it == true){
                handler.postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                }
                    ,1000)
            }else{
                handler.postDelayed({
                    startActivity(Intent(this, StartActivity::class.java))
                }
                    ,1000)
            }
        })
    }
}