package me.vikas.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import me.vikas.newsapp.data.repository.NewsSourceRepository
import me.vikas.newsapp.data.repository.TopHeadlineRepository
import me.vikas.newsapp.di.ActivityContext
import me.vikas.newsapp.di.ActivityScope
import me.vikas.newsapp.ui.base.ViewModelProviderFactory
import me.vikas.newsapp.ui.newssource.NewsSourceActivity
import me.vikas.newsapp.ui.newssource.NewsSourceAdapter
import me.vikas.newsapp.ui.newssource.NewsSourceViewModel
import me.vikas.newsapp.ui.topheadline.TopHeadlineActivity
import me.vikas.newsapp.ui.topheadline.TopHeadlineAdapter
import me.vikas.newsapp.ui.topheadline.TopHeadlineViewModel
import me.vikas.newsapp.utils.ArticleClick
import me.vikas.newsapp.utils.SourceClick


@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @ActivityScope
    @Provides
    fun provideTopHeadlineViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadlineViewModel {
        return ViewModelProvider(
            activity, ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }


    @ActivityScope
    @Provides
    fun provideNewsSourceViewModel(newsSourceRepository: NewsSourceRepository): NewsSourceViewModel {
        return ViewModelProvider(
            activity, ViewModelProviderFactory(NewsSourceViewModel::class) {
                NewsSourceViewModel(newsSourceRepository)
            })[NewsSourceViewModel::class.java]
    }


    @ActivityScope
    @Provides
    fun provideArticleClickHandler(): ArticleClick {
        val topHeadlineActivity = activity as TopHeadlineActivity
        return { article ->
            topHeadlineActivity.openArticle(article)
        }
    }

    @ActivityScope
    @Provides
    fun provideSourceClickHandler(): SourceClick {
        val newsSourceActivity = activity as NewsSourceActivity
        return { source ->
            newsSourceActivity.openArticle(source)
        }
    }



    @ActivityScope
    @Provides
    fun provideTopHeadlineAdapter(onArticleClick: @JvmSuppressWildcards ArticleClick) =
        TopHeadlineAdapter(onArticleClick, mutableListOf())


    @ActivityScope
    @Provides
    fun provideNewsSourceAdapter(onSourceClick: @JvmSuppressWildcards SourceClick) =
        NewsSourceAdapter(onSourceClick, mutableListOf())

}