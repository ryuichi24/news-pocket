package com.ryuichi24.newspocket.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ryuichi24.newspocket.models.Note

@Dao
interface NoteDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: Note): Long

    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE ownerArticleId LIKE :articleId")
    fun getAllNotesByArticleId(articleId: Int): LiveData<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)
}