package com.ryuichi24.newspocket.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class ArticleWithTag(
    @Embedded val article: Article,
    @ColumnInfo(name = "name")
    val tagName: String?,
)