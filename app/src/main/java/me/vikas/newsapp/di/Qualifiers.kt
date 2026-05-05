package me.vikas.newsapp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ActivityContext

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ArticleClickHandler

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchArticleClickHandler

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsCategoryClickHandler