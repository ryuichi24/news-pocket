package com.ryuichi24.newspocket.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.NewsResponse
import com.ryuichi24.newspocket.models.Tag
import com.ryuichi24.newspocket.repository.ArticleRepository
import com.ryuichi24.newspocket.repository.TagRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val articleRepository: ArticleRepository,
    private val tagRepository: TagRepository
) : ViewModel() {

    private val _topHeadlineNews: MutableLiveData<NewsResponse> = MutableLiveData()
    val topHeadlineNews: LiveData<NewsResponse> = _topHeadlineNews

    fun getTopHeadlines(countryCode: String, pageNumber: Int) = viewModelScope.launch {
        try {
            val response = articleRepository.getTopHeadlines(countryCode, pageNumber)
            _topHeadlineNews.value = response.body()
        } catch (e: Exception) {
            // TODO: update Internet Connection Error handling
            println("Internet connection error")
        }

    }

    // articles

    fun saveArticle(article: Article) = viewModelScope.launch {
        articleRepository.insert(article)
    }

    fun updateArticle(article: Article) = viewModelScope.launch {
        articleRepository.update(article)
    }

    fun getSavedArticles() = articleRepository.getSavedArticles()

    fun getSavedArticlesByTagId(tagId: Int) = articleRepository.getSavedArticlesByTagId(tagId)

    fun getArticlesWithTag() = articleRepository.getAllSavedArticlesWithTag()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        articleRepository.deleteArticle(article)
    }

    // tags

    fun getAllTags() = tagRepository.getAllTags()

    fun addTag(tag: Tag) = viewModelScope.launch {
        tagRepository.insert(tag)
    }

    fun deleteTag(tag: Tag) = viewModelScope.launch {
        tagRepository.deleteTag(tag)
    }

    // TODO: update it so that it dynamically gets both a country code and a page number from the user for future features
    init {
        getTopHeadlines("us", 1)
    }
}