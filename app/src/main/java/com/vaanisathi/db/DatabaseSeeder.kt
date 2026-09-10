package com.vaanisathi.db

object DatabaseSeeder {

    suspend fun seed(dao: PhraseDao) {
        // ── CLASSROOM MANAGEMENT (oral_language) ─────────────────────
        val classroomPhrases = listOf(
            FLNPhrase(phraseCode="CLS_001", domain="oral_language",
                hindiText="बैठो", santhaliOlChiki="ᱵᱮᱥᱮ ᱡᱚᱢ",
                santhaliRoman="Bese jom", audioFilename="cls_001_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-01",
                context="classroom_mgmt", validated=false),

            FLNPhrase(phraseCode="CLS_002", domain="oral_language",
                hindiText="खड़े हो जाओ", santhaliOlChiki="ᱫᱟᱝᱨᱟ ᱡᱚᱦᱚ",
                santhaliRoman="Dangra joho", audioFilename="cls_002_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-02",
                context="classroom_mgmt", validated=false),

            FLNPhrase(phraseCode="CLS_003", domain="oral_language",
                hindiText="शांत रहो", santhaliOlChiki="ᱥᱟᱱᱛᱮ ᱨᱟᱦ",
                santhaliRoman="Sante rah", audioFilename="cls_003_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-03",
                context="classroom_mgmt", validated=false),

            FLNPhrase(phraseCode="CLS_004", domain="oral_language",
                hindiText="सुनो", santhaliOlChiki="ᱦᱚᱨ",
                santhaliRoman="Hor", audioFilename="cls_004_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-04",
                context="instruction", validated=false),

            FLNPhrase(phraseCode="CLS_005", domain="oral_language",
                hindiText="देखो", santhaliOlChiki="ᱮᱱᱪᱮᱨᱮ",
                santhaliRoman="Enchere", audioFilename="cls_005_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-05",
                context="instruction", validated=false),

            FLNPhrase(phraseCode="CLS_006", domain="oral_language",
                hindiText="दोहराओ", santhaliOlChiki="ᱵᱩᱡᱷᱟᱚᱠᱚ",
                santhaliRoman="Bujhaoko", audioFilename="cls_006_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-06",
                context="instruction", validated=false),

            FLNPhrase(phraseCode="CLS_007", domain="oral_language",
                hindiText="बहुत अच्छा", santhaliOlChiki="ᱟᱞᱚᱢ ᱥᱮᱛᱮᱨ",
                santhaliRoman="Alom seter", audioFilename="cls_007_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-07",
                context="praise", validated=false),

            FLNPhrase(phraseCode="CLS_008", domain="oral_language",
                hindiText="शाबाश", santhaliOlChiki="ᱥᱮᱛᱮᱨ",
                santhaliRoman="Seter", audioFilename="cls_008_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-08",
                context="praise", validated=false),

            FLNPhrase(phraseCode="CLS_009", domain="oral_language",
                hindiText="यह क्या है", santhaliOlChiki="ᱱᱤ ᱠᱟᱹᱢᱤ",
                santhaliRoman="Ni kami", audioFilename="cls_009_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-09",
                context="instruction", validated=false),

            FLNPhrase(phraseCode="CLS_010", domain="oral_language",
                hindiText="आओ", santhaliOlChiki="ᱟᱦᱟᱸ",
                santhaliRoman="Ahang", audioFilename="cls_010_sat.mp3",
                gradeLevel=1, nipunCode="NB-OL-G1-10",
                context="classroom_mgmt", validated=false)
        )

        // ── NUMERACY ─────────────────────────────────────────────────
        val numbers = listOf(
            NumeracyEntry(1,"एक","Mit","ᱢᱤᱛ","num_001.mp3"),
            NumeracyEntry(2,"दो","Bar","ᱵᱟᱨ","num_002.mp3"),
            NumeracyEntry(3,"तीन","Pon","ᱯᱚᱱ","num_003.mp3"),
            NumeracyEntry(4,"चार","Upun","ᱩᱯᱩᱱ","num_004.mp3"),
            NumeracyEntry(5,"पांच","Mone","ᱢᱚᱱᱮ","num_005.mp3"),
            NumeracyEntry(6,"छह","Turui","ᱛᱩᱨᱩᱤ","num_006.mp3"),
            NumeracyEntry(7,"सात","Eae","ᱮᱮ","num_007.mp3"),
            NumeracyEntry(8,"आठ","Irel","ᱤᱨᱮᱞ","num_008.mp3"),
            NumeracyEntry(9,"नौ","Are","ᱟᱨᱮ","num_009.mp3"),
            NumeracyEntry(10,"दस","Gel","ᱜᱮᱞ","num_010.mp3")
        )

        dao.insertPhrases(classroomPhrases)
        numbers.forEach { dao.insertNumber(it) }
    }
}
