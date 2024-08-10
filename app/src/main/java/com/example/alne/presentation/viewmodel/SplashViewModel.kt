package com.example.alne.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.GlobalApplication
import com.example.alne.domain.repository.authRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: authRepository
) : ViewModel() {
    private val _autoLoginLiveData = MutableLiveData<Boolean?>()
    val autoLoginLiveData: LiveData<Boolean?> = _autoLoginLiveData

    init {
        val autoLogin: Boolean = GlobalApplication.prefManager.getAutoLogin()
        _autoLoginLiveData.postValue(autoLogin)
    }

}