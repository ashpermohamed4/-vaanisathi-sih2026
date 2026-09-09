package com.vaanisathi.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Core Room Entity representing a NIPUN Bharat aligned bilingual phrase.
 */
@Entity(tableName = "fln_phrases")
data class FLNPhrase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "phrase_code")
    val phraseCode: String,

    @ColumnInfo(name = "domain")
    val domain: String, // oral_language, phonological, print_awareness, reading, numeracy

    @ColumnInfo(name = "hindi_text")
    val hindiText: String,

    @ColumnInfo(name = "santhali_ol_chiki")
    val santhaliOlChiki: String,

    @ColumnInfo(name = "santhali_roman")
    val santhaliRoman: String,

    @ColumnInfo(name = "audio_filename")
    val audioFilename: String? = null,

    @ColumnInfo(name = "audio_validated")
    val audioValidated: Int = 0,

    @ColumnInfo(name = "grade_level")
    val gradeLevel: Int = 1,

    @ColumnInfo(name = "nipun_code")
    val nipunCode: String? = null,

    @ColumnInfo(name = "context")
    val context: String? = null,

    @ColumnInfo(name = "created_date")
    val createdDate: String? = null,

    @ColumnInfo(name = "validated_by")
    val validatedBy: String? = null
)

/**
 * Room Entity representing a structured bilingual FLN lesson.
 */
@Entity(tableName = "fln_lessons")
data class FLNLesson(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "lesson_code")
    val lessonCode: String,

    @ColumnInfo(name = "title_hindi")
    val titleHindi: String,

    @ColumnInfo(name = "title_santhali")
    val titleSanthali: String,

    @ColumnInfo(name = "grade")
    val grade: Int,

    @ColumnInfo(name = "domain")
    val domain: String,

    @ColumnInfo(name = "duration_minutes")
    val durationMinutes: Int = 20,

    @ColumnInfo(name = "steps_json")
    val stepsJson: String,

    @ColumnInfo(name = "worksheet_generated")
    val worksheetGenerated: Int = 0
)

/**
 * Room Entity for early childhood vocabulary items.
 */
@Entity(tableName = "vocabulary")
data class VocabularyWord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "hindi_word")
    val hindiWord: String,

    @ColumnInfo(name = "santhali_word")
    val santhaliWord: String,

    @ColumnInfo(name = "santhali_ol_chiki")
    val santhaliOlChiki: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "image_filename")
    val imageFilename: String? = null,

    @ColumnInfo(name = "audio_filename")
    val audioFilename: String? = null,

    @ColumnInfo(name = "grade_level")
    val gradeLevel: Int = 1
)

/**
 * Room Entity for foundational counting (1-10 / 1-20).
 */
@Entity(tableName = "numeracy")
data class NumeracyItem(
    @PrimaryKey
    val number: Int,

    @ColumnInfo(name = "hindi_word")
    val hindiWord: String,

    @ColumnInfo(name = "santhali_word")
    val santhaliWord: String,

    @ColumnInfo(name = "santhali_ol_chiki")
    val santhaliOlChiki: String,

    @ColumnInfo(name = "audio_filename")
    val audioFilename: String? = null
)
