package com.example.alne.viewmodel

import androidx.lifecycle.ViewModel
import com.example.alne.data.Network.SignUpResponse
import com.example.alne.data.model.User
import com.example.alne.domain.repository.authRepository
import com.example.alne.utils.RESPONSE_STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: authRepository
) : ViewModel() {


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