package com.ryuichi24.newspocket.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)