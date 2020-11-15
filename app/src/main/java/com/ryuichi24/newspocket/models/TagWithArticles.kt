package com.ryuichi24.newspocket.models

import androidx.room.Embedded
import androidx.room.Relation

data class TagWithArticles(
    @Embedded val tag: Tag,
    @Relation(
        parentColumn = "tagId",
        entityColumn = "ownerTagId"
    )
    val articles: List<Article>,

)