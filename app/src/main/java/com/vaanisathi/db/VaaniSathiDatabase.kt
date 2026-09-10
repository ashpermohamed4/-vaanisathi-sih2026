package com.vaanisathi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        FLNPhrase::class,
        FLNPhraseFts::class,
        NumeracyEntry::class,
        VocabularyEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VaaniSathiDatabase : RoomDatabase() {

    abstract fun phraseDao(): PhraseDao

    companion object {
        @Volatile private var INSTANCE: VaaniSathiDatabase? = null

        fun getInstance(context: Context): VaaniSathiDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    VaaniSathiDatabase::class.java,
                    "vaanisathi.db"
                )
                .addCallback(SeedCallback(context))
                .build()
                .also { INSTANCE = it }
            }
        }
    }

    // Auto-seed the database on first launch
    private class SeedCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                INSTANCE?.let { database ->
                    DatabaseSeeder.seed(database.phraseDao())
                }
            }
        }
    }
}
