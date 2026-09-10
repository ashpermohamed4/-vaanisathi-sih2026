package com.vaanisathi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhraseDao {

    // ── TIER 1 CORE: FTS5 fuzzy search ──────────────────────────────
    // This is your <5ms lookup — the key to ≤3sec E2E claim
    @Query("""
        SELECT fln_phrases.* FROM fln_phrases 
        INNER JOIN fln_phrases_fts ON fln_phrases.rowid = fln_phrases_fts.rowid
        WHERE fln_phrases_fts MATCH :query
        ORDER BY fln_phrases.gradeLevel ASC
        LIMIT 5
    """)
    suspend fun searchPhrases(query: String): List<FLNPhrase>

    // Exact match — fastest path (~1ms)
    @Query("SELECT * FROM fln_phrases WHERE hindiText = :exact LIMIT 1")
    suspend fun exactMatch(exact: String): FLNPhrase?

    // Get all phrases by domain
    @Query("SELECT * FROM fln_phrases WHERE domain = :domain ORDER BY phraseCode")
    suspend fun getPhrasesByDomain(domain: String): List<FLNPhrase>

    // Get all classroom management phrases
    @Query("SELECT * FROM fln_phrases WHERE context = 'classroom_mgmt'")
    suspend fun getClassroomPhrases(): List<FLNPhrase>

    // Reverse lookup — student taps → teacher gets Hindi
    @Query("SELECT * FROM fln_phrases WHERE santhaliOlChiki = :santhali LIMIT 1")
    suspend fun reverseLookup(santhali: String): FLNPhrase?

    // ── INSERT ───────────────────────────────────────────────────────
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrase(phrase: FLNPhrase)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(phrases: List<FLNPhrase>)

    // ── NUMERACY ─────────────────────────────────────────────────────
    @Query("SELECT * FROM numeracy ORDER BY number")
    suspend fun getAllNumbers(): List<NumeracyEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(entry: NumeracyEntry)

    // ── VOCABULARY ───────────────────────────────────────────────────
    @Query("SELECT * FROM vocabulary WHERE category = :category")
    suspend fun getVocabByCategory(category: String): List<VocabularyEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVocab(entry: VocabularyEntry)
}
