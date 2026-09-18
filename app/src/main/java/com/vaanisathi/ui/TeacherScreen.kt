package com.vaanisathi.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaanisathi.asr.VoskASREngine
import com.vaanisathi.db.FLNPhrase
import com.vaanisathi.db.VaaniSathiDatabase
import com.vaanisathi.nmt.BhashiniTranslator
import com.vaanisathi.tts.AudioPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val NavyBlue    = Color(0xFF0D1B6E)
val SaffronOrange = Color(0xFFEA580C)
val ForestGreen = Color(0xFF15803D)
val LightBlue   = Color(0xFFDBEAFE)
val LiveGreen   = Color(0xFF22C55E)

fun countCommonChars(a: String, b: String): Int {
    val s = a.toCharArray().toHashSet()
    return b.toCharArray().count { it in s }
}

@Composable
fun TeacherScreen(onStudentMode: () -> Unit) {
    val context = LocalContext.current
    val scope   = rememberCoroutineScope()
    val db      = remember { VaaniSathiDatabase.getInstance(context) }
    val asr     = remember { VoskASREngine(context) }
    val player  = remember { AudioPlayer(context) }

    val asrState by asr.state.collectAsState()

    var recognizedHindi by remember { mutableStateOf("") }
    var matchedPhrase   by remember { mutableStateOf<FLNPhrase?>(null) }
    var latencyMs       by remember { mutableStateOf(0L) }
    var isListening     by remember { mutableStateOf(false) }
    var tier2Result     by remember { mutableStateOf("") }
    var tier2Loading    by remember { mutableStateOf(false) }
    var tier2Error      by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { asr.initialize() }

    LaunchedEffect(asrState) {
        when (val s = asrState) {
            is VoskASREngine.ASRState.Idle -> isListening = false
            is VoskASREngine.ASRState.Loading -> isListening = false
            is VoskASREngine.ASRState.Listening -> isListening = true
            is VoskASREngine.ASRState.Result -> {
                recognizedHindi = s.text
                latencyMs       = s.latencyMs
                isListening     = false
                tier2Result     = ""
                tier2Error      = false
                tier2Loading    = false

                scope.launch {
                    var found = db.phraseDao().exactMatch(s.text)

                    if (found == null)
                        found = db.phraseDao()
                            .searchPhrases(s.text).firstOrNull()

                    if (found == null) {
                        for (w in s.text.trim().split(" ")) {
                            if (w.length >= 2) {
                                found = db.phraseDao()
                                    .searchPhrases(w).firstOrNull()
                                if (found != null) break
                            }
                        }
                    }

                    if (found == null) {
                        val all = db.phraseDao().getAllPhrases()
                        val best = all.maxByOrNull { p ->
                            countCommonChars(s.text, p.hindiText)
                        }
                        if (best != null &&
                            countCommonChars(s.text, best.hindiText) >= 2)
                            found = best
                    }

                    matchedPhrase = found

                    if (found != null) {
                        delay(300)
                        // SPEAK Santhali for student to hear
                        player.playFromAssets(
                            filename = found.audioFilename,
                            fallbackText = found.santhaliRoman
                        )
                    } else {
                        // TIER 2: Bhashini API
                        tier2Loading = true
                        val r = BhashiniTranslator
                            .translateHindiToSanthali(s.text)
                        tier2Loading = false
                        if (r.success && r.santhaliText.isNotBlank()) {
                            tier2Result = r.santhaliText
                            delay(300)
                            player.speakNow(r.santhaliText)
                        } else {
                            tier2Error = true
                        }
                    }
                }
            }
            is VoskASREngine.ASRState.Error -> isListening = false
        }
    }

    DisposableEffect(Unit) {
        onDispose { asr.release(); player.shutdown() }
    }

    val inf = rememberInfiniteTransition(label = "p")
    val pScale by inf.animateFloat(1f, 1.35f,
        infiniteRepeatable(tween(1000), RepeatMode.Restart), label="s")
    val pAlpha by inf.animateFloat(0.7f, 0f,
        infiniteRepeatable(tween(1000), RepeatMode.Restart), label="a")
    val dotA by inf.animateFloat(1f, 0.2f,
        infiniteRepeatable(tween(600), RepeatMode.Reverse), label="d")

    Column(
        Modifier.fillMaxSize().background(Color(0xFFF8FAFF))
    ) {
        // TOP BAR
        Box(
            Modifier.fillMaxWidth().background(NavyBlue)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column {
                Text("VaaniSathi", color = Color.White,
                    fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("वाणीसाथी — PALASH Teacher Mode",
                    color = Color.White.copy(0.8f), fontSize = 13.sp)
            }
            FilledTonalButton(
                onClick = onStudentMode,
                modifier = Modifier.align(Alignment.CenterEnd),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFF1E293B),
                    contentColor = Color(0xFFFCD34D)),
                shape = RoundedCornerShape(20.dp),
                contentPadding = PaddingValues(14.dp, 6.dp)
            ) { Text("Student Mode →", fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold) }
        }

        Column(
            Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // STATUS CARD
            when (asrState) {
                is VoskASREngine.ASRState.Loading ->
                    Card(Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp)) {
                        Column(Modifier.fillMaxWidth().padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Loading Vosk Hindi model (42 MB)...",
                                fontSize = 14.sp, color = NavyBlue,
                                fontWeight = FontWeight.Medium)
                            Spacer(Modifier.height(12.dp))
                            LinearProgressIndicator(
                                Modifier.fillMaxWidth().height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = SaffronOrange, trackColor = LightBlue)
                        }
                    }
                is VoskASREngine.ASRState.Error ->
                    Card(Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color(0xFFFEE2E2)),
                        shape = RoundedCornerShape(14.dp)) {
                        Text((asrState as VoskASREngine.ASRState.Error).message,
                            color = Color(0xFFDC2626), fontSize = 13.sp,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center)
                    }
                else ->
                    Card(Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(1.dp)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp),
                            horizontalArrangement = Arrangement.Center) {
                            Text("1. Tap mic  2. Speak Hindi  3. Hear Santhali",
                                fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                                color = NavyBlue, textAlign = TextAlign.Center)
                        }
                    }
            }

            Spacer(Modifier.height(24.dp))

            // MIC BUTTON
            Box(Modifier.size(150.dp), contentAlignment = Alignment.Center) {
                if (isListening) {
                    Canvas(Modifier.fillMaxSize()) {
                        drawCircle(Color.Red.copy(alpha = pAlpha),
                            (size.minDimension/2f)*pScale,
                            style = Stroke(4.dp.toPx()))
                        drawCircle(Color.Red.copy(alpha = pAlpha*0.5f),
                            (size.minDimension/2f)*(pScale*0.85f),
                            style = Stroke(2.dp.toPx()))
                    }
                }
                FloatingActionButton(
                    onClick = {
                        if (isListening) {
                            asr.stopListening(); isListening = false
                        } else {
                            recognizedHindi = ""; matchedPhrase = null
                            tier2Result = ""; tier2Error = false
                            isListening = true; asr.startListening()
                        }
                    },
                    containerColor = if (isListening) SaffronOrange else NavyBlue,
                    shape = CircleShape, modifier = Modifier.size(100.dp),
                    elevation = FloatingActionButtonDefaults.elevation(6.dp, 10.dp)
                ) {
                    Icon(if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                        "Mic", tint = Color.White, modifier = Modifier.size(46.dp))
                }
            }

            Spacer(Modifier.height(8.dp))
            if (isListening) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Box(Modifier.size(10.dp)
                        .background(LiveGreen.copy(dotA), CircleShape))
                    Spacer(Modifier.width(6.dp))
                    Text("LIVE — Speak Hindi now", fontSize = 12.sp,
                        fontWeight = FontWeight.Bold, color = ForestGreen)
                }
            }
            Text(if (isListening) "Listening..."
                 else "Tap mic → speak Hindi → hear Santhali",
                fontSize = 13.sp, color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp))

            Spacer(Modifier.height(20.dp))

            // RECOGNIZED HINDI
            if (recognizedHindi.isNotBlank()) {
                Card(Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(LightBlue),
                    shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Recognized Hindi", fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = NavyBlue.copy(0.75f))
                        Spacer(Modifier.height(4.dp))
                        Text(recognizedHindi, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, color = NavyBlue)
                    }
                }
                Spacer(Modifier.height(12.dp))
            }

            // TIER 1 RESULT
            matchedPhrase?.let { phrase ->
                Card(Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color.White),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp)) {
                    Column(Modifier.fillMaxWidth().padding(20.dp)) {
                        Row(Modifier.fillMaxWidth(),
                            Arrangement.SpaceBetween) {
                            Text("✅ Tier 1 — Offline",
                                fontSize = 11.sp, fontWeight = FontWeight.Bold,
                                color = ForestGreen)
                            Text(phrase.nipunCode,
                                fontSize = 10.sp, color = Color.Gray)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(phrase.hindiText, fontSize = 18.sp,
                            color = Color(0xFF6B7280))
                        Spacer(Modifier.height(6.dp))
                        // SANTHALI OL CHIKI — BIG
                        Text(phrase.santhaliOlChiki, fontSize = 36.sp,
                            fontWeight = FontWeight.Bold, color = NavyBlue,
                            lineHeight = 44.sp)
                        Text(phrase.santhaliRoman, fontSize = 14.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF6B7280))
                        Spacer(Modifier.height(14.dp))
                        Button(
                            onClick = {
                                player.playFromAssets(
                                    phrase.audioFilename,
                                    phrase.santhaliRoman)
                            },
                            colors = ButtonDefaults.buttonColors(
                                ForestGreen, Color.White),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.VolumeUp, "Play",
                                Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("🔊 Play Santhali Audio",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(Modifier.height(10.dp))
                        val sec = "%.1f".format(latencyMs/1000f)
                        val isUnder3s = latencyMs < 3000
                        SuggestionChip(onClick = {},
                            label = { Text(
                                if (isUnder3s) "⏱ ${sec}s  ✅ Tier 1 Offline <3s"
                                else "⏱ ${sec}s  ⚠️ >3s (phrase too long)",
                                fontSize = 12.sp,
                                color = if (isUnder3s) ForestGreen else Color(0xFFEA580C)
                            )},
                            shape = RoundedCornerShape(10.dp),
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (isUnder3s) Color(0xFFDCFCE7) 
                                                 else Color(0xFFFFF7ED)
                            ), border = null)
                    }
                }
            }

            // TIER 2 LOADING
            if (tier2Loading) {
                Card(Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0xFFFFF7ED)),
                    shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Tier 2: Bhashini API (Govt. of India)...",
                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFEA580C))
                        Spacer(Modifier.height(10.dp))
                        LinearProgressIndicator(Modifier.fillMaxWidth(),
                            color = Color(0xFFEA580C))
                    }
                }
            }

            // TIER 2 RESULT
            if (tier2Result.isNotBlank()) {
                Card(Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0xFFF0FDF4)),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFF86EFAC))) {
                    Column(Modifier.fillMaxWidth().padding(20.dp)) {
                        Row(Modifier.fillMaxWidth(),
                            Arrangement.SpaceBetween) {
                            Text("✅ Tier 2 — Bhashini (Online)",
                                fontSize = 12.sp, fontWeight = FontWeight.Bold,
                                color = ForestGreen)
                            Text("Online Mode",
                                fontSize = 10.sp, color = Color.Gray)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(tier2Result, fontSize = 28.sp,
                            fontWeight = FontWeight.Bold, color = NavyBlue)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { player.speakNow(tier2Result) },
                            colors = ButtonDefaults.buttonColors(
                                ForestGreen, Color.White),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) { Text("🔊 Speak Translation",
                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold) }
                    }
                }
            }

            // TIER 2 ERROR
            if (tier2Error && recognizedHindi.isNotBlank()
                && matchedPhrase == null && !tier2Loading) {
                Card(Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(Color(0xFFFFF7ED)),
                    shape = RoundedCornerShape(16.dp)) {
                    Column(Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⚠️ Not in offline dictionary",
                            fontSize = 14.sp, fontWeight = FontWeight.Bold,
                            color = Color(0xFFEA580C))
                        Spacer(Modifier.height(6.dp))
                        Text("Bhashini API offline.\nConnect internet for Tier 2\nor try: बैठो, शाबाश, सुनो, देखो, आओ",
                            fontSize = 13.sp, color = Color(0xFF92400E),
                            textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
