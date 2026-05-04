package me.vikas.newsapp.data.api

import me.vikas.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder().addHeader(
            "X-Api-Key", BuildConfig.API_KEY
        ).build()

        return chain.proceed(newRequest)
    }

}