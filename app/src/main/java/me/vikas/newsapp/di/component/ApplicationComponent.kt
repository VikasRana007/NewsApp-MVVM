package me.vikas.newsapp.di.component

import android.content.Context
import dagger.Component
import me.vikas.newsapp.NewsApplication
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.repository.TopHeadlineRepository
import me.vikas.newsapp.di.ApplicationContext
import me.vikas.newsapp.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    //fun getTopHeadlineRepository(): TopHeadlineRepository

}