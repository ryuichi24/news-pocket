package com.ryuichi24.newspocket.repository

import com.ryuichi24.newspocket.api.NewsPocketService

class NewsRepository(private val apiService: NewsPocketService) {

    suspend fun getTopHeadlines(countryCode: String, pageNumber: Int) =
        apiService.getTopHeadlines(countryCode, pageNumber)
}