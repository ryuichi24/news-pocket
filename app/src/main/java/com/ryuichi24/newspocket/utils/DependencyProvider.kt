package com.ryuichi24.newspocket.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ryuichi24.newspocket.api.createNewsPocketService
import com.ryuichi24.newspocket.db.ArticleDatabase
import com.ryuichi24.newspocket.repository.NewsRepository
import com.ryuichi24.newspocket.ui.viewModels.NewsPocketViewModel
import com.ryuichi24.newspocket.ui.viewModels.ViewModelFactory

object DependencyProvider {

    private val apiService = createNewsPocketService()

    fun provideViewModel(mainActivity: AppCompatActivity): NewsPocketViewModel {
        val articleDAO = ArticleDatabase.getDatabase(mainActivity).getArticleDAO()

        return ViewModelProvider(
            mainActivity,
            ViewModelFactory(NewsRepository(apiService, articleDAO))
        ).get(NewsPocketViewModel::class.java)
    }
}