package me.vikas.newsapp.di.module

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import me.vikas.newsapp.BuildConfig
import me.vikas.newsapp.data.api.ApiKeyInterceptor
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.local.AppDatabase
import me.vikas.newsapp.data.local.AppDatabaseService
import me.vikas.newsapp.data.local.DatabaseService
import me.vikas.newsapp.di.DatabaseName
import me.vikas.newsapp.utils.DefaultDispatcherProvider
import me.vikas.newsapp.utils.DispatcherProvider
import me.vikas.newsapp.utils.NetworkHelper
import me.vikas.newsapp.utils.NetworkHelperImpl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {


    @Provides
    @Singleton
    fun provideUserAgent(): String {
        return "MyNewsApp/${BuildConfig.VERSION_NAME} " + "(Android ${Build.VERSION.RELEASE}; " + "${Build.MODEL})"
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor, userAgent: String
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor(userAgent))
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

    @DatabaseName
    @Provides
    fun provideDataBaseName(): String {
        return "news_app_database"
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context, @DatabaseName databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, databaseName
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun providesAppDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()


    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
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


    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
            CREATE TABLE IF NOT EXISTS sync_headline_time (
                syncKey TEXT NOT NULL,
                lastSyncTime INTEGER NOT NULL,
                PRIMARY KEY(syncKey)
            )
        """.trimIndent()
            )
        }
    }
}