package me.vikas.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
     fun getAllArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAllArticle(articles: List<ArticleEntity>)

    @Query("DELETE FROM article")
     fun deleteAllArticles()

    @Transaction
     fun deleteAndInsertArticles(articles: List<ArticleEntity>) {
        deleteAllArticles()
        insertAllArticle(articles)
    }

}