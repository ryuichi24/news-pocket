package com.ryuichi24.newspocket.api

import com.ryuichi24.newspocket.models.NewsResponse
import com.ryuichi24.newspocket.utils.ConstantProperties.API_BASE_URL
import com.ryuichi24.newspocket.utils.ConstantProperties.API_KEY
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun createNewsPocketService(): NewsPocketService {
    val retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(NewsPocketService::class.java)
}

interface NewsPocketService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}