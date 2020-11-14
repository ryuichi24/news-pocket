package com.ryuichi24.newspocket.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles",
    foreignKeys = [
        ForeignKey(
            entity = Tag::class,
            childColumns = ["ownerTagId"],
            parentColumns = ["tagId"]
        )
    ]
)
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String,
    val ownerTagId: Int?
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var articleId: Int? = null
}