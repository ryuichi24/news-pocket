package com.ryuichi24.newspocket.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.Note

@Database(
    entities = [Article::class, Note::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class NewsPocketDatabase: RoomDatabase() {

    abstract fun getArticleDAO(): ArticleDAO
    abstract  fun getNoteDAO(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: NewsPocketDatabase? = null

        fun getDatabase(context: Context): NewsPocketDatabase {
            val hasInstance = INSTANCE != null
            if(hasInstance) return INSTANCE!!

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsPocketDatabase::class.java,
                    "article.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}