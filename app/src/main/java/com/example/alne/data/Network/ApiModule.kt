package com.example.alne.data.Network


import android.util.Log
import com.example.alne.GlobalApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.alne.utils.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient{ //로깅 인터셉터 추가
        val loggingInterceptor = HttpLoggingInterceptor { message ->
            Log.d(
                "Logging",
                "RetrofitClient - log() called: $message "
            )
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        // 기본 파라미터 추가
        val baseParameterInterceptor: Interceptor = object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                if(originalRequest.headers["Auth"] == "false"){
                    val finalRequest = chain.request().newBuilder()
                        .removeHeader("Auth")
                        .build()
                    return chain.proceed(finalRequest)
                }
                // Auth 파라미터 추가
                val finalRequest = originalRequest.newBuilder().addHeader("Authorization", "Bearer ${GlobalApplication.prefManager.getUserToken().accessToken}").build()
                return chain.proceed(finalRequest)
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(baseParameterInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(provideBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthApi{
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeService(retrofit: Retrofit): RecipeApi{
        return retrofit.create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFridgeService(retrofit: Retrofit): FridgeApi{
        return retrofit.create(FridgeApi::class.java)
    }


}