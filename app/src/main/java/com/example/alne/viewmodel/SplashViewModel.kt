package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alne.model.KakaoUser
import com.example.alne.repository.repository
import com.example.flo.Network.AuthResponse
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashViewModel: ViewModel() {

    private val repository = repository()
    private val _kakaoMyServerLoginRespond = MutableLiveData<AuthResponse>()
    val kakaoMyServerLoginRespond: LiveData<AuthResponse> = _kakaoMyServerLoginRespond

    fun kakaoLogin(){
        UserApiClient.instance.me { user, error ->
            Log.d("kakao_id", user?.id.toString())
            Log.d("kakao_nickname", user?.kakaoAccount?.name.toString() )
            Log.d("kakao_age", user?.kakaoAccount?.profile?.nickname.toString() )
            Log.d("kakao_email", user?.kakaoAccount?.profile?.profileImageUrl.toString())
            repository.kakaoLogin(KakaoUser(user?.id!!, user?.kakaoAccount?.name.toString(), user?.kakaoAccount?.email.toString())).enqueue(object: Callback<AuthResponse>{
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>,
                ) {
                    _kakaoMyServerLoginRespond.value = response.body()
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.d("kakaoLogin", t.message.toString())
                }

            })
        }
    }

}