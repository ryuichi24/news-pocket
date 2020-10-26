package com.ryuichi24.newspocket.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.NewsResponse
import com.ryuichi24.newspocket.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsPocketViewModel(private val repository: NewsRepository): ViewModel() {

    private val _topHeadlineNews: MutableLiveData<NewsResponse> = MutableLiveData()
    val topHeadlineNews: LiveData<NewsResponse> = _topHeadlineNews

    fun getTopHeadlines(countryCode: String, pageNumber: Int) = viewModelScope.launch {
        val response = repository.getTopHeadlines(countryCode, pageNumber)
        _topHeadlineNews.value = response.body()
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.upsert(article)
    }

    fun getSavedArticles() = repository.getSavedArticles()

    // TODO: update it so that it dynamically gets both a country code and a page number from the user for future features
    init {
        getTopHeadlines("us", 1)
    }
}