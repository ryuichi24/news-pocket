package com.ryuichi24.newspocket.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "tags"
)
data class Tag(
    val name: String,
    ): Serializable {
    @PrimaryKey(autoGenerate = true)
    var tagId: Int? = null

    // to deal with ArrayAdapter for spinner
    override fun toString(): String {
        return name
    }
}