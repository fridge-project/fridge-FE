package com.example.alne.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.alne.Network.SignUpResponse
import com.example.alne.data.model.User
import com.example.alne.repository.authRepository
import com.example.alne.utils.RESPONSE_STATUS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(private var application: Application) : AndroidViewModel(application) {

    private val authRepository = authRepository(application)
    fun signUp(user: User, completion: (RESPONSE_STATUS, String?) -> Unit){
        authRepository.signUp(user).enqueue(object: Callback<SignUpResponse>{
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>,
            ) {
                var res = response.body()
                when(response.code()){
                    201 -> {
                        completion(RESPONSE_STATUS.OKAY, res.toString())
                    }

                    409 -> {
                        completion(RESPONSE_STATUS.FAIL, res.toString())
                    }
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                completion(RESPONSE_STATUS.NETWORK_ERROR, t.message.toString())
            }
        })
    }
}