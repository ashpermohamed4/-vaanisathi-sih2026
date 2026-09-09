package com.vaanisathi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * VaaniSathi Room SQLite Database Singleton
 * Version 1: 50 Starter Phrases, Numeracy, Vocabulary, Lessons
 */
@Database(
    entities = [
        FLNPhrase::class,
        FLNLesson::class,
        VocabularyWord::class,
        NumeracyItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VaaniSathiDatabase : RoomDatabase() {

    abstract fun phraseDao(): PhraseDao

    companion object {
        @Volatile
        private var INSTANCE: VaaniSathiDatabase? = null

        fun getDatabase(context: Context): VaaniSathiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VaaniSathiDatabase::class.java,
                    "vaanisathi.db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
