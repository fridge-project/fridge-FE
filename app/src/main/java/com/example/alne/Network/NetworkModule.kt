package com.example.alne.Network
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


// http://ec2-54-175-121-98.compute-1.amazonaws.com:8080/
const val BASE_URL = "http://10.0.2.2:3000"

fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
//        .addConverterFactory(nullOnEmptyConverterFactory)
        .addConverterFactory(GsonConverterFactory.create())
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