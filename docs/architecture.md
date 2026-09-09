# 🏛️ VaaniSathi Architecture & Engineering Specification

**Smart India Hackathon 2026 | Problem Statement ID: 26042**  
**Offline AI Voice Bridge for Mother-Tongue FLN Education**

---

## 1. System Philosophy: The 100% Offline Edge Mandate

Primary schools and Anganwadi centers in tribal blocks across Jharkhand (Santhal Pargana region: Dumka, Pakur, Sahibganj, Godda, Jamtara) operate in deep digital shadow zones with zero or intermittent cellular connectivity and low-cost hardware (≤2 GB RAM Android tablets).

VaaniSathi is architected with a strict **Zero-Cloud, Edge-Native** design:
1. **Zero External API Calls**: All speech recognition, database lookups, and phonetics execute on-device.
2. **Deterministic Real-Time Latency**: ≤ 1.6s end-to-end latency for 95% of classroom voice interactions (beating the ≤3s hackathon limit).
3. **Zero Hallucination**: Verified bilingual curriculum phrases are human-audited with native Ol Chiki typography and pronunciation.

---

## 2. Dual-Tier Engine Architecture

```
                                  [ Teacher Speaks Hindi ]
                                              │
                                              ▼
                             ┌──────────────────────────────────┐
                             │  Vosk Small Hindi ASR (On-Device) │
                             │  - 42 MB Kaldi-based Edge Engine  │
                             │  - Latency: ~1,093ms – 1,635ms   │
                             └─────────────────┬────────────────┘
                                               │
                                       Recognized Text
                                               │
                                               ▼
                             ┌──────────────────────────────────┐
                             │   Intent & SQLite FTS5 Matcher   │
                             │   - Levenshtein Distance & Token │
                             │   - Latency: < 5ms               │
                             └─────────────────┬────────────────┘
                                               │
                       ┌───────────────────────┴───────────────────────┐
                       │                                               │
             [ Match Found (95% Core) ]                    [ Novel Sentence (5%) ]
                       │                                               │
                       ▼                                               ▼
     ┌──────────────────────────────────┐            ┌──────────────────────────────────┐
     │   TIER 1: Deterministic Audio    │            │   TIER 2: Extended AI Bridge     │
     │   - Pre-recorded Native Speaker  │            │   - IndicTrans2 INT8 Quantized   │
     │     Santhali MP3                 │            │   - Ol Chiki Script Generator    │
     │   - Ol Chiki Unicode Rendering   │            │   - eSpeak-NG Offline Phonetics  │
     │   - Latency: Instant (<10ms)     │            │   - Latency: 5–8s (Edge fallback)│
     └─────────────────┬────────────────┘            └─────────────────┬────────────────┘
                       │                                               │
                       └───────────────────────┬───────────────────────┘
                                               │
                                               ▼
                             ┌──────────────────────────────────┐
                             │  Classroom Display & Speaker     │
                             │  - High-Contrast Ol Chiki UI     │
                             │  - High-Fidelity Audio Playback  │
                             │  - Student Visual Card Response  │
                             └──────────────────────────────────┘
```

---

## 3. Storage & Memory Footprint Budget

VaaniSathi fits comfortably inside Android devices with strict 2 GB RAM limitations:

| Component | Storage Size | Runtime RAM | Latency |
|-----------|--------------|-------------|---------|
| **Vosk Hindi ASR Model** (`vosk-model-small-hi-0.22`) | 42 MB | ~65 MB | 1.1s – 1.6s |
| **Room / SQLite DB + 50 Seed Phrases** | < 2 MB | < 5 MB | < 5 ms |
| **Native Santhali MP3 Audio Assets (50 phrases)** | ~18 MB | Streamed buffer | < 10 ms |
| **Jetpack Compose UI & App Binary** | ~25 MB | ~80 MB | 60 fps |
| **Tier 2 IndicTrans2 INT8 (Optional Module)** | ~300 MB | ~180 MB | 5s – 8s |
| **TOTAL TIER 1 CLASSROOM FOOTPRINT** | **~87 MB** | **~150 MB** | **~1.1s – 1.6s** |

---

## 4. Student-to-Teacher Communication Bridge (Reverse Channel)

To eliminate the need for an expensive and error-prone Santhali ASR engine on low-cost devices, student feedback uses **Interactive Visual Cards**:
1. Students tap large, high-contrast, illustrated Santhali phrase cards (e.g., *"আমাকে সাহায্য করুন"* / *"ᱤᱧ ᱥᱟᱦᱚᱡ ᱫᱚᱨᱠᱟᱨ"*).
2. The app instantly executes a reverse Room DB lookup (<5ms).
3. The app renders the Hindi translation on the teacher's screen and plays the Hindi voice prompt.
4. Total latency: **< 100 ms**, enabling 100% reliable two-way dialogue without linguistic misunderstanding.

---

## 5. Ol Chiki Typography & Unicode Rendering

Santhali is officially written in the **Ol Chiki** script (Unicode range: `U+1C50` to `U+1C7F`), standardized by Pandit Raghunath Murmu.

- Font integration: Bundled embedded `NotoSansOlChiki-Regular.ttf` in `app/src/main/assets/fonts/`.
- Fallback support: Every database record maintains a dual representation (`santhali_ol_chiki` and `santhali_roman`) ensuring readability across all Android devices and older hardware.
- High-contrast visual tokens tailored for low-luminosity Anganwadi classrooms.

---

## 6. Academic Audit & Content Provenance

- **Institutional Validator**: Department of Tribal and Regional Languages, *Sido Kanhu Murmu University (SKMU), Dumka, Jharkhand*.
- **Integrity Guarantee**: Content bundles are cryptographically signed to prevent curriculum tampering in field deployments.
