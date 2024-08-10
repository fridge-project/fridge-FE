package com.example.alne.view.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import com.example.alne.MainActivity
import com.example.alne.databinding.ActivitySplashBinding
import com.example.alne.viewmodel.SplashViewModel
import com.example.alne.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        setTheme(R.style.Theme_ALNE_SplashScreen)
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

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