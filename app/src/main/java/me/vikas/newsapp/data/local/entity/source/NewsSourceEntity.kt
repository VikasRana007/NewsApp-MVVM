package me.vikas.newsapp.data.local.entity.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_source")
data class NewsSourceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sourceId")
    val sourceId: Int = 0,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "country")
    val country: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "url")
    val url: String
)
