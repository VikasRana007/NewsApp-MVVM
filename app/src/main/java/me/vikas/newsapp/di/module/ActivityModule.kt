package me.vikas.newsapp.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
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


}