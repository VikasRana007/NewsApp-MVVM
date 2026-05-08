package me.vikas.newsapp.data.local.entity.topheadlines

import androidx.room.ColumnInfo

data class SourceEntity(
    @ColumnInfo(name = "sourceId")
    val id: String?,
    @ColumnInfo(name = "sourceName")
    val name: String = ""
)