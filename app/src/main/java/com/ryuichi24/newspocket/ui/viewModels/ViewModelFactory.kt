package com.ryuichi24.newspocket.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryuichi24.newspocket.repository.ArticleRepository
import com.ryuichi24.newspocket.repository.TagRepository


class ViewModelFactory(private val articleRepository: ArticleRepository, private val tagRepository: TagRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsPocketViewModel(articleRepository, tagRepository) as T
    }
}