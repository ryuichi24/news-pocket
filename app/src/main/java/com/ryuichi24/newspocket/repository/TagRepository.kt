package com.ryuichi24.newspocket.repository

import com.ryuichi24.newspocket.db.TagDAO
import com.ryuichi24.newspocket.models.Tag

class TagRepository(private val tagDAO: TagDAO) {

    fun getAllTags() = tagDAO.getAllTags()

    suspend fun upsert(tag: Tag) = tagDAO.upsert(tag)

    suspend fun deleteTag(tag: Tag) = tagDAO.deleteTag(tag)
}