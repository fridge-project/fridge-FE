package com.example.alne.Network
import android.util.Log
import com.example.alne.GlobalApplication
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


// http://ec2-54-175-121-98.compute-1.amazonaws.com:8080/
const val BASE_URL = "http://10.0.2.2:3000"

fun getRetrofit(): Retrofit {

    val builder = OkHttpClient.Builder()
        .addInterceptor(baseParameterInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    var gson = GsonBuilder().setLenient().create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(builder)
        .build()
    return retrofit
}

private val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object :
        Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() == 0L) null else nextResponseBodyConverter.convert(value)
    }
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

//로깅 인터셉터 추가
val loggingInterceptor = HttpLoggingInterceptor { message ->
    Log.d(
        "Logging",
        "RetrofitClient - log() called: $message "
    )
}.apply {
    level = HttpLoggingInterceptor.Level.BODY
}