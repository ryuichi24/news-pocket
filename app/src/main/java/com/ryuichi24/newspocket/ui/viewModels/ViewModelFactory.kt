package com.ryuichi24.newspocket.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryuichi24.newspocket.repository.NewsRepository

class ViewModelFactory(private val repository: NewsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsPocketViewModel(repository) as T
    }
}