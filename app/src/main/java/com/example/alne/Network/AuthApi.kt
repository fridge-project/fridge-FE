package com.example.alne.Network

import com.example.alne.data.model.User
import com.example.alne.data.model.ProfileRespond
import com.example.alne.data.model.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApi {

    @POST(API.SIGNUP)
    @Headers("Auth: false")
    fun signUp(@Body user: User): Call<SignUpResponse>

    @POST(API.LOGIN)
    @Headers("Auth: false")
    fun login(@Body user: User): Call<LoginResponse>

    @POST(API.LOGOUT)
    @Headers("Auth: false")
    fun logout(@Body token: Token): Call<SignUpResponse>

    @Multipart
    @POST("/userImage")
    fun saveUserProfileImage(
        @Part file: MultipartBody.Part,
        @Part("userId") userId: RequestBody
    ): Call<AuthResponse>


//    @POST("/profile")
//    fun getUserProfile(
//        @Body userId: UserId
//    ):Call<ProfileRespond>


}