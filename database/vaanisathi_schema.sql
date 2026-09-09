-- ========================================================
-- VaaniSathi (वाणीसाथी) — FLN Database Schema
-- Smart India Hackathon 2026 | Problem Statement ID: 26042
-- Offline SQLite / Room Database for Mother-Tongue Education
-- Target: Santhali (Ol Chiki `sat_Olck`) ↔ Hindi
-- ========================================================

PRAGMA foreign_keys = ON;
PRAGMA encoding = 'UTF-8';

-- --------------------------------------------------------
-- Table 1: fln_phrases
-- Core NIPUN Bharat aligned starter phrases (Oral Language,
-- Phonological Awareness, Print Awareness, Reading, Numeracy)
-- --------------------------------------------------------
DROP TABLE IF EXISTS fln_phrases;
CREATE TABLE fln_phrases (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    phrase_code TEXT NOT NULL UNIQUE,       -- e.g. 'CLS_001', 'NUM_001', 'INS_001'
    domain TEXT NOT NULL,                  -- 'oral_language', 'phonological', 'print_awareness', 'reading', 'numeracy'
    hindi_text TEXT NOT NULL,              -- Devanagari Hindi text
    santhali_ol_chiki TEXT NOT NULL,       -- Ol Chiki Unicode script (sat_Olck)
    santhali_roman TEXT NOT NULL,          -- Romanized phonetic Santhali fallback
    audio_filename TEXT,                   -- Pre-recorded native audio filename (e.g. 'cls_001_sat.mp3')
    audio_validated INTEGER DEFAULT 0,     -- 0 = pending native audit, 1 = verified by SKMU Dumka
    grade_level INTEGER DEFAULT 1,         -- Grade 1, 2, or 3 (Balvatika to Primary)
    nipun_code TEXT,                       -- NIPUN Bharat learning outcome code (e.g. 'NB-OL-G1-01')
    context TEXT,                          -- 'classroom_mgmt', 'lesson', 'praise', 'instruction'
    created_date TEXT DEFAULT (datetime('now')),
    validated_by TEXT                      -- Academic/Native validator info
);

-- --------------------------------------------------------
-- Table 2: fln_lessons
-- Structured Grade 1-3 bilingual classroom lesson plans
-- --------------------------------------------------------
DROP TABLE IF EXISTS fln_lessons;
CREATE TABLE fln_lessons (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    lesson_code TEXT NOT NULL UNIQUE,      -- e.g. 'LES_G1_NUM_01'
    title_hindi TEXT NOT NULL,             -- Hindi lesson title
    title_santhali TEXT NOT NULL,          -- Ol Chiki Santhali title
    grade INTEGER NOT NULL,                -- Grade level (1, 2, 3)
    domain TEXT NOT NULL,                  -- NIPUN domain
    duration_minutes INTEGER DEFAULT 20,   -- Recommended classroom time
    steps_json TEXT NOT NULL,              -- JSON array of structured interactive steps
    worksheet_generated INTEGER DEFAULT 0  -- 0 = pending, 1 = generated
);

-- --------------------------------------------------------
-- Table 3: vocabulary
-- Essential early childhood vocabulary (Body, Nature, Classroom, Animals, Colors)
-- --------------------------------------------------------
DROP TABLE IF EXISTS vocabulary;
CREATE TABLE vocabulary (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    hindi_word TEXT NOT NULL,              -- Hindi term
    santhali_word TEXT NOT NULL,           -- Santhali Romanized
    santhali_ol_chiki TEXT NOT NULL,       -- Santhali Ol Chiki Unicode
    category TEXT NOT NULL,                -- 'classroom', 'nature', 'animals', 'body', 'colors'
    image_filename TEXT,                   -- Visual illustration asset filename
    audio_filename TEXT,                   -- Pronunciation audio asset
    grade_level INTEGER DEFAULT 1
);

-- --------------------------------------------------------
-- Table 4: numeracy
-- Foundational counting and numeracy (1-10 / 1-20)
-- --------------------------------------------------------
DROP TABLE IF EXISTS numeracy;
CREATE TABLE numeracy (
    number INTEGER PRIMARY KEY,            -- Numerical value (1, 2, 3...)
    hindi_word TEXT NOT NULL,              -- e.g. 'एक', 'दो'
    santhali_word TEXT NOT NULL,           -- e.g. 'Mit', 'Bar'
    santhali_ol_chiki TEXT NOT NULL,       -- e.g. 'ᱢᱤᱛ', 'ᱵᱟᱨ'
    audio_filename TEXT                    -- Pronunciation audio asset
);

-- --------------------------------------------------------
-- Indexes for Sub-5ms Offline Edge Query Performance
-- --------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_fln_phrases_domain ON fln_phrases(domain);
CREATE INDEX IF NOT EXISTS idx_fln_phrases_hindi ON fln_phrases(hindi_text);
CREATE INDEX IF NOT EXISTS idx_fln_phrases_code ON fln_phrases(phrase_code);
CREATE INDEX IF NOT EXISTS idx_vocab_category ON vocabulary(category);
CREATE INDEX IF NOT EXISTS idx_lessons_grade ON fln_lessons(grade);
