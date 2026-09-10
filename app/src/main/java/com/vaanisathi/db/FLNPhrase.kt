package com.vaanisathi.db

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

// Main phrase table
@Entity(tableName = "fln_phrases")
data class FLNPhrase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val phraseCode: String,        // e.g. "CLS_001"
    val domain: String,            // oral_language|numeracy|phonological
    val hindiText: String,         // बैठो
    val santhaliOlChiki: String,   // ᱵᱮᱥᱮ ᱡᱚᱢ
    val santhaliRoman: String,     // Bese jom
    val audioFilename: String,     // cls_001_sat.mp3
    val gradeLevel: Int,           // 1, 2, or 3
    val nipunCode: String,         // NB-OL-G1-01
    val context: String,           // classroom_mgmt|praise|instruction
    val validated: Boolean = false
)

// FTS4 virtual table for fuzzy Hindi text search
// This gives <5ms lookup across 50+ phrases
@Fts4(contentEntity = FLNPhrase::class)
@Entity(tableName = "fln_phrases_fts")
data class FLNPhraseFts(
    val hindiText: String
)

// Numeracy table
@Entity(tableName = "numeracy")
data class NumeracyEntry(
    @PrimaryKey val number: Int,
    val hindiWord: String,
    val santhaliWord: String,
    val santhaliOlChiki: String,
    val audioFilename: String
)

// Vocabulary table
@Entity(tableName = "vocabulary")
data class VocabularyEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val hindiWord: String,
    val santhaliWord: String,
    val santhaliOlChiki: String,
    val category: String,         // numbers|colors|body|animals|classroom
    val audioFilename: String,
    val gradeLevel: Int
)
