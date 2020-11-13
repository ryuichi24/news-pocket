package com.ryuichi24.newspocket.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = Article::class,
            childColumns = ["ownerArticleId"],
            parentColumns = ["articleId"]
        )
    ]
)
data class Note(
    val text: String,
    val date: Date,
    val ownerArticleId: Int
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}