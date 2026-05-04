package me.vikas.newsapp.di.module

import android.content.Context
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import me.vikas.newsapp.BuildConfig
import me.vikas.newsapp.NewsApplication
import me.vikas.newsapp.data.api.ApiKeyInterceptor
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.di.ApplicationContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val newsApplication: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext() = newsApplication.applicationContext

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor())
            .addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideJsonSerializer(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
            prettyPrint = false
        }
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideRetrofit(converterFactory: Converter.Factory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(converterFactory).build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }


    /**
     * Below Provider is used to make the OKHttp Client Same of Coil library to fetch images
     * because some news CDN Sources restrict the api without proper header, and we are only
     * sending header with api key. & user agent in the Retrofit only.
     * What the purpose of Below provider is : To use our OkHttpClient instead of
     * the default one
     */
    @Provides
    @Singleton
    fun provideImageLoader(context: Context, okHttpClient: OkHttpClient): ImageLoader {
        return ImageLoader.Builder(context).components {
            add(OkHttpNetworkFetcherFactory(okHttpClient))
        }.crossfade(true).build()
    }

}