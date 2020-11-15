package com.ryuichi24.newspocket.repository

import com.ryuichi24.newspocket.api.NewsPocketService
import com.ryuichi24.newspocket.db.ArticleDAO
import com.ryuichi24.newspocket.models.Article

class ArticleRepository(
    private val apiService: NewsPocketService,
    private val articleDAO: ArticleDAO
) {
    // <---------------------------------------API call---------------------------------------->
    suspend fun getTopHeadlines(countryCode: String, pageNumber: Int) =
        apiService.getTopHeadlines(countryCode, pageNumber)

    // <---------------------------------------Data Access---------------------------------------->
    fun getSavedArticles() = articleDAO.getAllArticles()

    fun getAllSavedArticlesWithTag() = articleDAO.getAllArticlesWithTag()

    fun getSavedArticlesByTagId(tagId: Int) = articleDAO.getArticlesByTagId(tagId)

    suspend fun insert(article: Article) = articleDAO.insert(article)

    suspend fun update(article: Article) = articleDAO.update(article)

    suspend fun deleteArticle(article: Article) = articleDAO.deleteArticle(article)
}