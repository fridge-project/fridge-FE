package com.example.alne.repository

import android.app.Application
import com.example.alne.Network.AuthApi
import com.example.alne.Network.getRetrofit
import com.example.alne.data.model.Token
import com.example.alne.data.model.User
import com.example.alne.data.model.UserId
import okhttp3.MultipartBody
import okhttp3.RequestBody


class authRepository(val application: Application) {

    private val userService = getRetrofit().create(AuthApi::class.java)


    fun signUp(user: User) = userService.signUp(user)

    fun login(user: User) = userService.login(user)

    fun logout(token: Token) = userService.logout(token)

    fun saveUserProfileImage(file: MultipartBody.Part, userId: RequestBody) = userService.saveUserProfileImage(file,userId)

    fun getUserProfile(userId: UserId) = userService.getUserProfile(userId)

}
