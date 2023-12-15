package com.dicoding.secucare.service

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiConfig {
    fun getApiService(token: String? = null): ApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG){
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val retrofit = Retrofit.Builder()
    }
}