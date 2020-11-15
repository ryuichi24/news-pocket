package com.ryuichi24.newspocket.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.ArticleWithTag
import com.ryuichi24.newspocket.models.TagWithArticles

@Dao
interface ArticleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Update
    suspend fun update(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Transaction
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM articles LEFT JOIN tags on tags.tagId = articles.ownerTagId")
    fun getAllArticlesWithTag(): LiveData<List<ArticleWithTag>>

    @Query("SELECT * FROM articles WHERE ownerTagId LIKE :tagId")
    fun getArticlesByTagId(tagId: Int): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}