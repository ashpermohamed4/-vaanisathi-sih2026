package com.vaanisathi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for high-speed offline query operations (<5ms).
 */
@Dao
interface PhraseDao {

    // ----------------------------------------------------
    // Phrase Lookups (Tier 1 Matcher)
    // ----------------------------------------------------
    @Query("SELECT * FROM fln_phrases WHERE hindi_text = :query LIMIT 1")
    suspend fun findExactMatch(query: String): FLNPhrase?

    @Query("SELECT * FROM fln_phrases WHERE hindi_text LIKE '%' || :query || '%' LIMIT 5")
    suspend fun findFuzzyMatches(query: String): List<FLNPhrase>

    @Query("SELECT * FROM fln_phrases WHERE phrase_code = :code LIMIT 1")
    suspend fun getByPhraseCode(code: String): FLNPhrase?

    @Query("SELECT * FROM fln_phrases WHERE domain = :domain ORDER BY id ASC")
    fun getPhrasesByDomain(domain: String): Flow<List<FLNPhrase>>

    @Query("SELECT * FROM fln_phrases ORDER BY id ASC")
    fun getAllPhrases(): Flow<List<FLNPhrase>>

    @Query("SELECT COUNT(*) FROM fln_phrases")
    suspend fun getPhraseCount(): Int

    // ----------------------------------------------------
    // Numeracy & Vocabulary
    // ----------------------------------------------------
    @Query("SELECT * FROM numeracy ORDER BY number ASC")
    fun getAllNumeracy(): Flow<List<NumeracyItem>>

    @Query("SELECT * FROM vocabulary WHERE category = :category ORDER BY id ASC")
    fun getVocabularyByCategory(category: String): Flow<List<VocabularyWord>>

    @Query("SELECT * FROM vocabulary ORDER BY category, id ASC")
    fun getAllVocabulary(): Flow<List<VocabularyWord>>

    // ----------------------------------------------------
    // Lessons
    // ----------------------------------------------------
    @Query("SELECT * FROM fln_lessons WHERE grade = :grade ORDER BY id ASC")
    fun getLessonsByGrade(grade: Int): Flow<List<FLNLesson>>

    @Query("SELECT * FROM fln_lessons WHERE lesson_code = :lessonCode LIMIT 1")
    suspend fun getLessonByCode(lessonCode: String): FLNLesson?

    // ----------------------------------------------------
    // Seed Insertion
    // ----------------------------------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(phrases: List<FLNPhrase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumeracy(items: List<NumeracyItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocabulary(words: List<VocabularyWord>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLessons(lessons: List<FLNLesson>)
}
