package me.vikas.newsapp.data.api

import me.vikas.newsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(private val userAgent: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("X-Api-Key", BuildConfig.API_KEY)
            .header("User-Agent", userAgent)
            .build()

        return chain.proceed(newRequest)
    }

}