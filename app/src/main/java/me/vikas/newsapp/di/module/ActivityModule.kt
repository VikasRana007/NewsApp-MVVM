package me.vikas.newsapp.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import me.vikas.newsapp.ui.countrywisenews.CountryWiseNewsAdapter
import me.vikas.newsapp.ui.languagewisenews.LanguageWiseAdapter
import me.vikas.newsapp.ui.newslist.NewsListAdapter
import me.vikas.newsapp.ui.newssource.NewsSourceAdapter
import me.vikas.newsapp.ui.searchnews.NewsSearchAdapter
import me.vikas.newsapp.ui.topheadline.TopHeadlineAdapter

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    /**
     * Adapters Initialization by Hilt
     */
    @ActivityScoped
    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(mutableListOf())

    @ActivityScoped
    @Provides
    fun provideNewsSourceAdapter() = NewsSourceAdapter(mutableListOf())

    @ActivityScoped
    @Provides
    fun provideNewsCategoryAdapter() = NewsListAdapter(mutableListOf())

    @ActivityScoped
    @Provides
    fun provideCountryListAdapter() = CountryWiseNewsAdapter(mutableListOf())

    @ActivityScoped
    @Provides
    fun provideLanguageListAdapter() = LanguageWiseAdapter(mutableListOf())

    @ActivityScoped
    @Provides
    fun provideNewsSearchAdapter() = NewsSearchAdapter(mutableListOf())
}