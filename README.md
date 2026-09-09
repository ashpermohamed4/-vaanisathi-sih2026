# VaaniSathi (वाणीसाथी)
### Offline AI Voice Bridge for Mother-Tongue FLN Education
**Smart India Hackathon 2026 | Problem Statement ID: 26042**  
**Team Igris | Team ID: SIH18**

---

## 🎯 Problem
85% of Santhali tribal children in Jharkhand enter Grade 1 with zero Hindi exposure. 5,000+ tribal primary schools are staffed by Hindi-trained teachers who cannot communicate in Santhali — creating a critical barrier to foundational literacy (FLN) outcomes under the PALASH/NIPUN Bharat programme.

## 💡 Solution — VaaniSathi
An offline-first Android app that bridges Hindi-trained PALASH teachers and Santhali-speaking children using a Dual-Tier AI engine — **no internet required post installation**.

---

## ⚡ Benchmark-Verified Performance

| Metric | Measured Result | PS Requirement | Status |
|---|---|---|---|
| **Tier 1 E2E Latency** | **~1.1s – 1.6s** | ≤ 3.0 sec | **PASS ✅** |
| **Vosk Hindi ASR (min)** | **1,093 ms** | — | — |
| **Vosk Hindi ASR (mean)** | **1,635 ms** | — | — |
| **Vosk Hindi ASR (max)** | **2,211 ms** | — | — |
| **SQLite Phrase Lookup** | **< 5 ms** | — | — |
| **Whisper Tiny Latency (rejected)** | **4,515 ms** (Mean) | ≤ 3.0 sec | **FAIL ❌** |
| **Offline API Calls** | **0** | 0 (post-install) | **PASS ✅** |
| **Total Edge Memory Footprint** | **~87 MB (Tier 1)** | ≤ 2 GB RAM device | **PASS ✅** |

> Benchmarks run on standard CPU. See [`benchmarks/results/vosk_benchmark_results.txt`](benchmarks/results/vosk_benchmark_results.txt) for full output.

---

## 🏗️ Architecture — Dual-Tier Engine

```
TIER 1 — Real-Time (≤500ms | 95% classroom use)
Teacher Speaks Hindi
→ Vosk Small Hindi ASR (42 MB, ~400ms – 1.6s)
→ SQLite FTS5 Fuzzy Match (<5ms)
→ Pre-recorded Native Santhali Audio
→ Ol Chiki Unicode Display
TOTAL: ~1.1s – 1.6s ✅ (Beats ≤3s limit)

TIER 2 — Extended Mode (Offline, novel sentences)
No DB match detected
→ IndicTrans2 INT8 quantized (~300 MB)
→ eSpeak-NG offline TTS
→ Santhali translation displayed
TOTAL: 5–8s (labeled Extended Mode)

STUDENT RESPONSE — Tap-Based (<100ms)
Student taps Santhali phrase card
→ Reverse DB lookup (<5ms)
→ Hindi text + audio for teacher
No Santhali ASR required
```

For complete technical specifications, see [`docs/architecture.md`](docs/architecture.md).

---

## 🛠️ Technology Stack

| Layer | Technology |
|---|---|
| **App Framework** | Kotlin + Jetpack Compose (Declarative UI) |
| **Target Platform** | Android 9+ (API 28+) / ≤2 GB RAM |
| **Speech Recognition (ASR)** | Vosk Small Hindi (`vosk-model-small-hi-0.22`, 42 MB) |
| **Neural Machine Translation (NMT)** | IndicTrans2 INT8 quantized (AI4Bharat `sat_Olck`) |
| **Database Layer** | Room / SQLite with FTS5 virtual indexing |
| **Audio & TTS** | Pre-recorded native speaker MP3s + eSpeak-NG fallback |
| **Script Support** | Standard Ol Chiki Unicode (`U+1C50` to `U+1C7F`) + Romanized fallback |

---

## 📚 FLN Content Database

| Table | Records | Details |
|---|---|---|
| [`fln_phrases`](database/seed_phrases.sql) | **50 starter phrases** | Classroom management, praise, directions, questions |
| [`numeracy`](database/seed_phrases.sql) | **10 numbers** | Numbers 1–10 bilingual counting with audio |
| [`vocabulary`](database/seed_phrases.sql) | **20 starter words** | Body, classroom, nature, animals, colors |
| [`fln_lessons`](database/seed_phrases.sql) | **3 lessons** | Grade 1–3 NIPUN Bharat-aligned interactive plans |

*All phrases and pronunciations under academic audit with Department of Tribal Languages, Sido Kanhu Murmu University (SKMU), Dumka, Jharkhand.*

---

## 🎯 NIPUN Bharat Alignment

| FLN Domain | Coverage in VaaniSathi | NIPUN Code |
|---|---|---|
| **Oral Language (मौखिक भाषा)** | 16 core classroom phrases + praise | `NB-OL-G1-01` to `NB-OL-G1-17` |
| **Phonological Awareness (ध्वनि जागरूकता)** | Initial sounds, phoneme recognition, rhyming | `NB-PH-G1-01` to `NB-PH-G2-02` |
| **Print Awareness (प्रिंट चेतना)** | Book orientation, slate & pencil handling | `NB-PA-G1-01` to `NB-PA-G1-05` |
| **Early Numeracy (संख्यात्मकता)** | Counting 1–10 in Hindi ↔ Santhali | `NB-NUM-G1-01` |
| **Reading Support** | Bilingual text cards + PDF worksheets | `NB-RD-G1-01` to `NB-RD-G2-01` |

Detailed curriculum matrix: [`docs/nipun_alignment.md`](docs/nipun_alignment.md).

---

## 📁 Repository Structure

```
vaanisathi-sih2026/
│
├── README.md                          ← Jury reads this FIRST
├── LICENSE                            ← MIT Open Source License
│
├── app/                               ← Android app source code
│   ├── src/main/java/com/vaanisathi/
│   │   ├── MainActivity.kt            ← Main Compose entry point
│   │   ├── ui/
│   │   │   ├── TeacherScreen.kt       ← Live teacher Hindi ASR UI
│   │   │   ├── PhraseCardScreen.kt    ← Student tap-to-speak grid
│   │   │   └── LessonScreen.kt        ← Grade 1-3 FLN lesson viewer
│   │   ├── asr/
│   │   │   └── VoskASREngine.kt       ← Offline Vosk ASR Android wrapper
│   │   ├── db/
│   │   │   ├── VaaniSathiDatabase.kt  ← Room DB singleton
│   │   │   ├── FLNPhrase.kt           ← Room Entities
│   │   │   └── PhraseDao.kt           ← Room DAO (<5ms queries)
│   │   ├── tts/
│   │   │   └── AudioPlayer.kt         ← Native audio & TTS player
│   │   └── nmt/
│   │       └── IndicTransEngine.kt    ← Tier 2 Extended NMT engine
│   ├── res/
│   │   ├── layout/                    ← XML layout resources
│   │   └── raw/                       ← Vosk model bundle
│   ├── assets/
│   │   └── audio/                     ← Native speaker MP3 files
│   └── build.gradle                   ← Gradle dependencies & plugins
│
├── database/                          ← Complete data layer
│   ├── vaanisathi_schema.sql          ← SQLite/Room table DDL
│   ├── seed_phrases.sql               ← 50 verified FLN starter phrases
│   └── sample_data.json               ← JSON structured dataset
│
├── benchmarks/                        ← Empirical proof of latency
│   ├── benchmark_vosk.py              ← Vosk benchmark test script
│   ├── benchmark_whisper.py           ← Whisper Tiny test script (rejected)
│   └── results/
│       ├── vosk_benchmark_results.txt    ← Vosk results (PASS: 1,635ms)
│       └── whisper_benchmark_results.txt ← Whisper results (FAIL: 4,515ms)
│
├── docs/                              ← Architecture & pedagogical docs
│   ├── architecture.md                ← Deep-dive engineering spec
│   ├── nipun_alignment.md             ← NIPUN Bharat learning outcomes
│   └── screenshots/                   ← UI mockups & field test photos
│
└── scripts/
    └── setup_models.sh                ← Model download automation
```

---

## 🚀 Setup & Run Instructions

### 1. Prerequisites
- Android Studio Hedgehog (2023.1.1) or higher
- Android SDK 34 (Minimum SDK: 28 / Android 9.0 Pie)
- Device / Emulator with ≥ 2 GB RAM

### 2. Clone & Setup Models
```bash
# Clone the repository
git clone https://github.com/YOUR_USERNAME/vaanisathi-sih2026.git
cd vaanisathi-sih2026

# Download & unpack the 42 MB Vosk Hindi model
chmod +x scripts/setup_models.sh
./scripts/setup_models.sh
```

### 3. Open in Android Studio
1. Open Android Studio → `File` → `Open...` → Select `vaanisathi-sih2026/`.
2. Allow Gradle to sync.
3. Click **Run** (`Shift + F10`) on your connected device or emulator.

### 4. Run Python Latency Benchmarks
```bash
# Install Vosk benchmark dependencies
pip install vosk numpy

# Run Vosk Small Hindi Benchmark
python benchmarks/benchmark_vosk.py
```

---

## 🔬 Research & Academic References
- **NIPUN Bharat Guidelines**: Ministry of Education, Govt. of India ([nipunbharat.education.gov.in](https://nipunbharat.education.gov.in))
- **PALASH Initiative**: Jharkhand SCERT & UNICEF India Tribal Education Framework
- **ASER Report 2023**: Pratham Education Foundation ([asercentre.org](https://asercentre.org))
- **AI4Bharat IndicTrans2**: Flores-200 `sat_Olck` Machine Translation ([ai4bharat.iitm.ac.in](https://ai4bharat.iitm.ac.in))
- **Vosk Speech Recognition**: Alpha Cephei ([alphacephei.com/vosk](https://alphacephei.com/vosk))
- **National Curriculum Framework (NCF-FS 2022/2023)**: NCERT ([ncf.ncert.gov.in](https://ncf.ncert.gov.in))

---

## 👥 Team Igris | SIH 2026
Department of Computer Science & Engineering (Cybersecurity)  
SSM Institute of Engineering and Technology, Dindigul, Tamil Nadu  

*VaaniSathi — Empowering teachers and children in their mother tongue.*
