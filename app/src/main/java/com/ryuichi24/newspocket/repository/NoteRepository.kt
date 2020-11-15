package com.ryuichi24.newspocket.repository

import com.ryuichi24.newspocket.db.NoteDAO
import com.ryuichi24.newspocket.models.Note

class NoteRepository(
    private val noteDAO: NoteDAO
) {
    fun getAllNotes() = noteDAO.getAllNotes()

    fun getAllNotesByArticleId(articleId: Int) = noteDAO.getAllNotesByArticleId(articleId)

    suspend fun insert(note: Note) = noteDAO.insert(note)

    suspend fun update(note: Note) = noteDAO.update(note)

    suspend fun deleteNote(note: Note) = noteDAO.deleteNote(note)
}