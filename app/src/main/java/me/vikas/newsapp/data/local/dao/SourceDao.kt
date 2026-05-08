package me.vikas.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.local.entity.source.NewsSourceEntity

@Dao
interface SourceDao {

    @Query("SELECT * FROM news_source")
    fun getAllSources(): Flow<List<NewsSourceEntity>>

    @Insert
    fun insertAllSources(sources: List<NewsSourceEntity>)

    @Query("DELETE FROM news_source")
    fun deleteAllSources()

    @Transaction
    fun deleteAndInsertSources(sources: List<NewsSourceEntity>){
        deleteAllSources()
        insertAllSources(sources)
    }

}