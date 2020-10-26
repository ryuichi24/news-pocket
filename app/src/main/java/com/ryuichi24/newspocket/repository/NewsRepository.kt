package com.ryuichi24.newspocket.repository

import com.ryuichi24.newspocket.api.NewsPocketService
import com.ryuichi24.newspocket.db.ArticleDAO
import com.ryuichi24.newspocket.models.Article

class NewsRepository(
    private val apiService: NewsPocketService,
    private val articleDAO: ArticleDAO
) {
    // <---------------------------------------API call---------------------------------------->
    suspend fun getTopHeadlines(countryCode: String, pageNumber: Int) =
        apiService.getTopHeadlines(countryCode, pageNumber)

    // <---------------------------------------Data Access---------------------------------------->
    fun getSavedArticles() = articleDAO.getAllArticles()

    suspend fun upsert(article: Article) = articleDAO.upsert(article)

    suspend fun deleteArticle(article: Article) = articleDAO.deleteArticle(article)
}