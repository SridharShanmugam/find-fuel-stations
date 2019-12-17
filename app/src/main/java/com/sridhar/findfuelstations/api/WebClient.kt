package com.sridhar.findfuelstations.api

import com.sridhar.findfuelstations.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object WebClient {

    fun getWebClient(): Retrofit {
        val builder = Retrofit.Builder().apply {
            client(getOkHttpClient())
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }
        return builder.build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply { level = Level.BODY }
        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()
    }
}