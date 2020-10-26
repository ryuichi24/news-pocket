package com.ryuichi24.newspocket.utils

import androidx.lifecycle.ViewModelProvider
import com.ryuichi24.newspocket.api.createNewsPocketService
import com.ryuichi24.newspocket.repository.NewsRepository
import com.ryuichi24.newspocket.ui.viewModels.ViewModelFactory

object DependencyInjection {

    private val apiService = createNewsPocketService()
    private val newsRepository = NewsRepository(apiService)
    private val NewsPocketViewModelFactory = ViewModelFactory(newsRepository)

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return NewsPocketViewModelFactory
    }
}