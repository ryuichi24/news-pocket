package com.ryuichi24.newspocket.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ryuichi24.newspocket.api.createNewsPocketService
import com.ryuichi24.newspocket.db.NewsPocketDatabase
import com.ryuichi24.newspocket.repository.ArticleRepository
import com.ryuichi24.newspocket.repository.NoteRepository
import com.ryuichi24.newspocket.repository.TagRepository
import com.ryuichi24.newspocket.ui.viewModels.MainViewModel
import com.ryuichi24.newspocket.ui.viewModels.NoteViewModel
import com.ryuichi24.newspocket.ui.viewModels.NoteViewModelFactory
import com.ryuichi24.newspocket.ui.viewModels.ViewModelFactory

object DependencyProvider {

    fun provideViewModel(mainActivity: AppCompatActivity): MainViewModel {
        val apiService = createNewsPocketService()

        val database = NewsPocketDatabase.getDatabase(mainActivity)

        val articleDAO = database.getArticleDAO()
        val articleRepository = ArticleRepository(apiService, articleDAO)

        val tagDAO = database.getTagDAO()
        val tagRepository = TagRepository(tagDAO)

        val mainViewModel = ViewModelProvider(mainActivity, ViewModelFactory(articleRepository, tagRepository)).get(MainViewModel::class.java)

        return mainViewModel
    }

    fun provideNoteViewModel(mainActivity: AppCompatActivity): NoteViewModel {
        val noteDAO = NewsPocketDatabase.getDatabase(mainActivity).getNoteDAO()
        var noteRepository = NoteRepository(noteDAO)
        val noteViewModel = ViewModelProvider(mainActivity, NoteViewModelFactory(noteRepository)).get(NoteViewModel::class.java)

        return noteViewModel
    }

}