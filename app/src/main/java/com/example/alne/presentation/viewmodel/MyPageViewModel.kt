package com.example.alne.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.alne.GlobalApplication
import com.example.alne.Network.AuthResponse
import com.example.alne.Network.SignUpResponse
import com.example.alne.data.model.Profile
import com.example.alne.data.model.ProfileRespond
import com.example.alne.data.model.Token
import com.example.alne.data.model.UserId
import com.example.alne.repository.authRepository
import com.example.alne.utils.REPONSE_STATUS
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MyPageViewModel(application: Application) : AndroidViewModel(application) {

    val TAG = "MyPageViewModel"
    private val authRepository = authRepository(application)

    private val _userProfileLiveData = MutableLiveData<Profile>()
    val userProfileLiveData = _userProfileLiveData

    init {
//        if(GlobalApplication.prefManager.getUserToken() != null){
//            getUserProfile(GlobalApplication.prefManager.getUserToken()!!.userId)
//        }else{
//            _userProfileLiveData.postValue(null)
//        }
    }

    fun saveUserProfileImage(photoFile: File, userId: Int){
        var fileBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), photoFile)
        var file: MultipartBody.Part = MultipartBody.Part.createFormData("file", photoFile.name, fileBody)
        var userId: RequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(userId))


        authRepository.saveUserProfileImage(file,userId).enqueue(object: retrofit2.Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val res = response.body()
                Log.d(TAG, "onResponse: {${res.toString()}}")
                if(response.isSuccessful){
                    when(res?.status){
                        200 -> {
                            Log.d(TAG, "onResponse: Success")
                        }
                        else -> {
                            Log.d(TAG, "onResponse: Fail")
                        }
                    }
                }

            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: onFailure{${t.message.toString()}}")
            }
        })
    }

    fun getUserProfile(userId: Int) = authRepository.getUserProfile(UserId(userId,null)).enqueue(object:
        Callback<ProfileRespond>{
        override fun onResponse(call: Call<ProfileRespond>, response: Response<ProfileRespond>) {
            val res = response.body()
            Log.d(TAG, "getUserProfile_onReponse:{${res.toString()}}")
            when(res?.status){
                200 -> {
                    _userProfileLiveData.postValue(res.data)
                }
                else -> {
                    Log.d(TAG, "getUserProfile_Fail")
                }
            }
        }

        override fun onFailure(call: Call<ProfileRespond>, t: Throwable) {
            Log.d(TAG, "getUserProfile_onFailure: {${t.message.toString()}")
        }

    })

    fun logout(token: Token, completion: (REPONSE_STATUS) -> Unit) = authRepository.logout(token).enqueue(object: Callback<SignUpResponse>{
        override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
            when(response.code()){
                200 -> {
                    GlobalApplication.prefManager.deleteUserData()
                    completion(REPONSE_STATUS.OKAY)
                }

                403 -> {
                    completion(REPONSE_STATUS.FAIL)
                }
            }
        }

        override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
            completion(REPONSE_STATUS.NETWORK_ERROR)
        }

    })





}