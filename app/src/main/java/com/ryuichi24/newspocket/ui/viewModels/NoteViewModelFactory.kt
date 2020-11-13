package com.ryuichi24.newspocket.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ryuichi24.newspocket.repository.NoteRepository

class NoteViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}