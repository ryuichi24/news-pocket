package com.ryuichi24.newspocket.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ryuichi24.newspocket.models.Tag

@Dao
interface TagDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: Tag): Long

    @Query("SELECT * FROM tags")
    fun getAllTags(): LiveData<List<Tag>>

    @Delete
    suspend fun deleteTag(tag: Tag)
}