package com.ryuichi24.newspocket.repository

import com.ryuichi24.newspocket.api.createNewsPocketService

class NewsRepository {

    suspend fun getTopHeadlines(countryCode: String, pageNumber: Int) =
        createNewsPocketService().getTopHeadlines(countryCode, pageNumber)
}