package com.example.alne.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alne.GlobalApplication
import com.example.alne.repository.authRepository

class SplashViewModel(private var application: Application) : AndroidViewModel(application) {

    private val authRepository = authRepository(application)
    private val _autoLoginLiveData = MutableLiveData<Boolean?>()
    val autoLoginLiveData: LiveData<Boolean?> = _autoLoginLiveData

    init {
        val autoLogin: Boolean = GlobalApplication.prefManager.getAutoLogin()
        _autoLoginLiveData.postValue(autoLogin)
    }

}