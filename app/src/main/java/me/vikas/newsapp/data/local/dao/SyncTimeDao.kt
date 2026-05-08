package me.vikas.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.vikas.newsapp.data.local.entity.synctime.SyncTopHeadlineTime

@Dao
interface SyncTimeDao {
    @Query("SELECT lastSyncTime FROM sync_headline_time WHERE syncKey = :key LIMIT 1")
    suspend fun getLastSyncTime(key: String): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(meta: SyncTopHeadlineTime)
}