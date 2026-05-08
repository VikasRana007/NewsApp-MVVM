package me.vikas.newsapp.data.local.entity.synctime

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_headline_time")
data class SyncTopHeadlineTime(
    @PrimaryKey val syncKey: String = "TOP_HEADLINES",
    @ColumnInfo(name = "lastSyncTime")
    val lastSyncTime: Long
)
