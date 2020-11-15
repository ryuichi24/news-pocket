package com.ryuichi24.newspocket.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ryuichi24.newspocket.models.Note
import com.ryuichi24.newspocket.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository): ViewModel() {

    fun getAllNotes() = repository.getAllNotes()

    fun getNotesByArticleId(articleId: Int) = repository.getAllNotesByArticleId(articleId)

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
    }

}