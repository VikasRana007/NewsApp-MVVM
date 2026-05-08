package me.vikas.newsapp.data.local.entity.topheadlines

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    val id: Int = 0,
    @ColumnInfo(name = "author")
    val author: String? = null,
    @ColumnInfo("content")
    val content: String? = null,
    @ColumnInfo("description")
    val description: String? = null,
    @ColumnInfo("publishedAt")
    val publishedAt: String,
    @Embedded val source: SourceEntity,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("urlToImage")
    val urlToImage: String? = null
)