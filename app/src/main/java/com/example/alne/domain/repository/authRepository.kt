package com.example.alne.domain.repository

import android.app.Application
import com.example.alne.data.Network.AuthApi
import com.example.alne.data.Network.getRetrofit
import com.example.alne.data.model.Token
import com.example.alne.data.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


class authRepository @Inject constructor(
    private val userService: AuthApi
) {
    fun signUp(user: User) = userService.signUp(user)

    fun login(user: User) = userService.login(user)

    fun logout(token: Token) = userService.logout(token)

    fun saveUserProfileImage(file: MultipartBody.Part, userId: RequestBody) = userService.saveUserProfileImage(file,userId)

    fun deleteAccount() = userService.deleteAccount()

    // fun getUserProfile(userId: UserId) = userService.getUserProfile(userId)

}
