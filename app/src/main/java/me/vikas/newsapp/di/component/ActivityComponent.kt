package me.vikas.newsapp.di.component

import dagger.Component
import me.vikas.newsapp.di.ActivityScope
import me.vikas.newsapp.di.module.ActivityModule
import me.vikas.newsapp.ui.newssource.NewsSourceActivity
import me.vikas.newsapp.ui.topheadline.TopHeadlineActivity

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)
    fun inject(activity: NewsSourceActivity)


}