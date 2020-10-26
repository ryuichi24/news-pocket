package com.ryuichi24.newspocket.db

import androidx.room.TypeConverter
import com.ryuichi24.newspocket.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(id = name, name = name)
    }
}