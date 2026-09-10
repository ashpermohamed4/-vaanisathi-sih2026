package com.vaanisathi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaanisathi.asr.VoskASREngine
import com.vaanisathi.db.FLNPhrase
import com.vaanisathi.db.VaaniSathiDatabase
import com.vaanisathi.tts.AudioPlayer
import kotlinx.coroutines.launch

// Brand colors — VaaniSathi
val NavyBlue = Color(0xFF0D1B6E)
val SaffronOrange = Color(0xFFEA580C)
val ForestGreen = Color(0xFF15803D)
val LightBlue = Color(0xFFDBEAFE)

@Composable
fun TeacherScreen(onStudentMode: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { VaaniSathiDatabase.getInstance(context) }
    val asr = remember { VoskASREngine(context) }
    val player = remember { AudioPlayer(context) }

    val asrState by asr.state.collectAsState()

    var recognizedHindi by remember { mutableStateOf("") }
    var matchedPhrase by remember { mutableStateOf<FLNPhrase?>(null) }
    var latencyMs by remember { mutableStateOf(0L) }
    var isListening by remember { mutableStateOf(false) }
    var statusMsg by remember { mutableStateOf("Ready — tap mic to speak Hindi") }

    // Initialize Vosk on first launch
    LaunchedEffect(Unit) {
        statusMsg = "Loading Vosk Hindi model (42 MB)..."
        asr.initialize()
    }

    // React to ASR state changes
    LaunchedEffect(asrState) {
        when (val s = asrState) {
            is VoskASREngine.ASRState.Idle -> {
                statusMsg = "Ready — tap mic to speak Hindi"
                isListening = false
            }
            is VoskASREngine.ASRState.Loading ->
                statusMsg = "Loading Vosk model..."
            is VoskASREngine.ASRState.Listening ->
                statusMsg = "Listening... speak in Hindi"
            is VoskASREngine.ASRState.Result -> {
                recognizedHindi = s.text
                latencyMs = s.latencyMs
                isListening = false
                // Tier 1: search DB
                scope.launch {
                    val exact = db.phraseDao().exactMatch(s.text)
                    val found = exact ?: db.phraseDao()
                        .searchPhrases(s.text).firstOrNull()
                    matchedPhrase = found
                    if (found != null) {
                        statusMsg = "✅ Match found — playing Santhali audio"
                        player.playFromAssets(found.audioFilename)
                    } else {
                        statusMsg = "No match — try Extended Mode (Tier 2)"
                    }
                }
            }
            is VoskASREngine.ASRState.Error ->
                statusMsg = "Error: ${s.message}"
        }
    }

    DisposableEffect(Unit) {
        onDispose { asr.release(); player.release() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFF))
    ) {
        // ── TOP BAR ────────────────────────────────────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavyBlue)
                .padding(16.dp)
        ) {
            Column {
                Text("VaaniSathi", color = Color.White,
                    fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("वाणीसाथी — PALASH Teacher Mode",
                    color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
            }
            TextButton(
                onClick = onStudentMode,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("Student Mode →", color = Color(0xFFFCD34D))
            }
        }

        // ── LATENCY BADGE ──────────────────────────────────────────
        if (latencyMs > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (latencyMs < 3000) ForestGreen else SaffronOrange)
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("⏱ Tier 1 E2E: ${latencyMs}ms",
                    color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Text(if (latencyMs < 3000) "✅ PASS" else "⚠ SLOW",
                    color = Color.White, fontSize = 13.sp)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── MIC BUTTON ─────────────────────────────────────────
            Spacer(modifier = Modifier.height(16.dp))
            Text(statusMsg, fontSize = 14.sp,
                color = Color(0xFF1E293B), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(20.dp))

            FloatingActionButton(
                onClick = {
                    if (isListening) {
                        asr.stopListening()
                        isListening = false
                    } else {
                        isListening = true
                        asr.startListening()
                    }
                },
                containerColor = if (isListening) SaffronOrange else NavyBlue,
                shape = CircleShape,
                modifier = Modifier.size(90.dp)
            ) {
                Icon(
                    if (isListening) Icons.Default.Stop else Icons.Default.Mic,
                    contentDescription = "Mic",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Text(
                if (isListening) "Recording..." else "Tap to Speak Hindi",
                fontSize = 12.sp, color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── ASR RESULT ─────────────────────────────────────────
            if (recognizedHindi.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = LightBlue),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Recognised Hindi:", fontSize = 11.sp,
                            color = Color(0xFF6B7280))
                        Text(recognizedHindi, fontSize = 20.sp,
                            fontWeight = FontWeight.Bold, color = NavyBlue)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // ── MATCHED PHRASE CARD ────────────────────────────────
            matchedPhrase?.let { phrase ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFDCFCE7)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("✅ Santhali Translation",
                                fontSize = 11.sp, color = ForestGreen,
                                fontWeight = FontWeight.Bold)
                            Text(phrase.nipunCode,
                                fontSize = 10.sp, color = Color.Gray)
                        }
                        Spacer(Modifier.height(8.dp))
                        // Ol Chiki Unicode
                        Text(phrase.santhaliOlChiki, fontSize = 32.sp,
                            color = NavyBlue, fontWeight = FontWeight.Bold)
                        Text(phrase.santhaliRoman, fontSize = 14.sp,
                            color = Color(0xFF6B7280))
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssistChip(onClick = {
                                phrase.audioFilename.let {
                                    player.playFromAssets(it)
                                }
                            }, label = { Text("🔊 Play Audio") })
                            AssistChip(onClick = {},
                                label = { Text(phrase.context) })
                        }
                    }
                }
            }
        }
    }
}
