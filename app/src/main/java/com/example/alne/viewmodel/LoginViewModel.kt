package com.example.alne.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.alne.GlobalApplication
import com.example.alne.Network.LoginResponse
import com.example.alne.data.model.User
import com.example.alne.repository.authRepository
import com.example.alne.utils.REPONSE_STATUS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = authRepository(application)

    fun login(user: User, completion: (REPONSE_STATUS) -> Unit){
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
                        completion(REPONSE_STATUS.OKAY)
                    }

                    401 -> {
                        completion(REPONSE_STATUS.FAIL)
                    }
                }
                Log.d("login_success", res.toString())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                completion(REPONSE_STATUS.NETWORK_ERROR)
            }
        })
    }
}