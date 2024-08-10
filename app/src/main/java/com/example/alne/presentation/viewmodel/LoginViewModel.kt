package com.example.alne.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.alne.GlobalApplication
import com.example.alne.data.Network.LoginResponse
import com.example.alne.data.model.User
import com.example.alne.domain.repository.authRepository
import com.example.alne.utils.RESPONSE_STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: authRepository
) : ViewModel() {

    fun login(user: User, completion: (RESPONSE_STATUS) -> Unit){
        authRepository.login(user).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>,
            ) {
                var res = response.body()
                when(response.code()){
                    200 -> {
                        //토큰 처리
                        GlobalApplication.prefManager.saveJwt(res!!.accessToken, res!!.refreshToken)
                        completion(RESPONSE_STATUS.OKAY)
                    }

                    401 -> {
                        completion(RESPONSE_STATUS.FAIL)
                    }
                }
                Log.d("login_success", res.toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                completion(RESPONSE_STATUS.NETWORK_ERROR)
            }
        })
    }
}