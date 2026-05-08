package me.vikas.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.vikas.newsapp.data.local.dao.ArticleDao
import me.vikas.newsapp.data.local.dao.SourceDao
import me.vikas.newsapp.data.local.dao.SyncTimeDao
import me.vikas.newsapp.data.local.entity.source.NewsSourceEntity
import me.vikas.newsapp.data.local.entity.synctime.SyncTopHeadlineTime
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity

@Database(
    entities = [ArticleEntity::class,
        NewsSourceEntity::class,
        SyncTopHeadlineTime::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    abstract fun sourceDao(): SourceDao

    abstract fun syncTimeDao(): SyncTimeDao

}
