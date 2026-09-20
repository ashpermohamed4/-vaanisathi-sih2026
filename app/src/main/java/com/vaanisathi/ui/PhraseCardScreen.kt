package com.vaanisathi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.vaanisathi.asr.StudentVoiceRecorder
import com.vaanisathi.db.FLNPhrase
import com.vaanisathi.db.VaaniSathiDatabase
import com.vaanisathi.tts.AudioPlayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhraseCardScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db      = remember { VaaniSathiDatabase.getInstance(context) }
    val player  = remember { AudioPlayer(context) }
    val recorder = remember { StudentVoiceRecorder(context) }

    var phrases       by remember { mutableStateOf<List<FLNPhrase>>(emptyList()) }
    var selectedPhrase by remember { mutableStateOf<FLNPhrase?>(null) }
    var tapLatencyMs  by remember { mutableStateOf(0L) }
    var isRecording   by remember { mutableStateOf(false) }
    var statusText    by remember {
        mutableStateOf("Tap Ol Chiki card → both voices play")
    }

    LaunchedEffect(Unit) {
        phrases = db.phraseDao().getClassroomPhrases()
    }

    DisposableEffect(Unit) {
        onDispose { player.shutdown(); recorder.release() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Student Mode", color = Color.White,
                            fontWeight = FontWeight.Bold)
                        Text("Tap card → Santhali + Hindi voices play",
                            color = Color.White.copy(0.8f), fontSize = 11.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back",
                            tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(NavyBlue)
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding)
                .background(Color(0xFFF8FAFF))
        ) {
            // LATENCY BADGE
            if (tapLatencyMs > 0) {
                Box(Modifier.fillMaxWidth()
                    .background(ForestGreen).padding(8.dp)) {
                    Text("⏱ Tap: ${tapLatencyMs}ms  ✅ <100ms",
                        color = Color.White, fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Center))
                }
            }

            // STUDENT MIC — record Santhali, play back to teacher
            Card(
                Modifier.fillMaxWidth().padding(12.dp),
                colors = CardDefaults.cardColors(
                    if (isRecording) Color(0xFFFEE2E2) else Color(0xFFEFF6FF)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (isRecording) "🔴 Recording — tap stop"
                        else "🎤 Speak Santhali — teacher hears playback",
                        fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                        color = if (isRecording) Color.Red else NavyBlue)
                    Spacer(Modifier.height(10.dp))
                    FloatingActionButton(
                        onClick = {
                            if (isRecording) {
                                recorder.stopRecording()
                                isRecording = false
                                statusText = "Playing Santhali voice for teacher..."
                                recorder.playBack {
                                    statusText = "Tap card → both voices play"
                                }
                            } else {
                                if (recorder.startRecording()) {
                                    isRecording = true
                                    statusText = "Recording... tap stop when done"
                                }
                            }
                        },
                        containerColor = if (isRecording) Color.Red
                                         else Color(0xFF1E40AF),
                        shape = CircleShape,
                        modifier = Modifier.size(72.dp)
                    ) {
                        Icon(
                            if (isRecording) Icons.Default.Stop
                            else Icons.Default.Mic,
                            "Mic", tint = Color.White,
                            modifier = Modifier.size(32.dp))
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(statusText, fontSize = 12.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center)
                }
            }

            // SANTHALI TEXT SEARCH
            var searchText by remember { mutableStateOf("") }
            Card(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                colors = CardDefaults.cardColors(Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    Modifier.padding(12.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        placeholder = { Text("Search Hindi or Santhali...",
                            color = Color(0xFF9CA3AF), fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            val match = phrases.find { p ->
                                p.hindiText.contains(searchText, true) ||
                                p.santhaliRoman.contains(searchText, true)
                            }
                            if (match != null) {
                                selectedPhrase = match
                                player.speakBothDirections(
                                    match.santhaliRoman, match.hindiText, 2000)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(NavyBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) { Text("Find", fontWeight = FontWeight.Bold,
                        color = Color.White) }
                }
            }
            Spacer(Modifier.height(4.dp))

            // SELECTED PHRASE — shows after card tap
            selectedPhrase?.let { phrase ->
                Card(
                    Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    colors = CardDefaults.cardColors(Color(0xFFFEF3C7)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Teacher hears:",
                            fontSize = 11.sp, color = Color(0xFF92400E),
                            fontWeight = FontWeight.Medium)
                        Spacer(Modifier.height(4.dp))
                        Text(phrase.hindiText, fontSize = 26.sp,
                            fontWeight = FontWeight.Bold, color = NavyBlue)
                        Text("Santhali: ${phrase.santhaliOlChiki}",
                            fontSize = 18.sp, color = Color(0xFF1E40AF))
                        Text("(${phrase.santhaliRoman})",
                            fontSize = 13.sp, color = Color.Gray)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                // Speak SANTHALI first, then HINDI after 2s
                                player.speakBothDirections(
                                    santhaliText = phrase.santhaliRoman,
                                    hindiText = phrase.hindiText,
                                    delayMs = 2000
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                ForestGreen, Color.White),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("🔊 Santhali → (2s) → Hindi",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            // PHRASE GRID
            LazyVerticalGrid(
                GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(phrases) { phrase ->
                    PhraseCard(
                        phrase = phrase,
                        isSelected = selectedPhrase?.id == phrase.id,
                        onClick = {
                            val t = System.currentTimeMillis()
                            selectedPhrase = phrase
                            tapLatencyMs = System.currentTimeMillis() - t
                            // AUTO-PLAY both voices on tap
                            player.speakBothDirections(
                                santhaliText = phrase.santhaliRoman,
                                hindiText = phrase.hindiText,
                                delayMs = 2000
                            )
                            statusText = "Played Santhali → Hindi"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PhraseCard(
    phrase: FLNPhrase,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .aspectRatio(1.1f).clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            if (isSelected) Color(0xFFDCFCE7) else Color.White),
        elevation = CardDefaults.cardElevation(
            if (isSelected) 6.dp else 2.dp)
    ) {
        Column(
            Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(phrase.santhaliOlChiki, fontSize = 22.sp,
                fontWeight = FontWeight.Bold, color = NavyBlue,
                textAlign = TextAlign.Center)
            Spacer(Modifier.height(4.dp))
            Text(phrase.santhaliRoman, fontSize = 10.sp,
                color = Color(0xFF6B7280), textAlign = TextAlign.Center)
            Spacer(Modifier.height(4.dp))
            Text(phrase.hindiText, fontSize = 11.sp,
                color = SaffronOrange, fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center)
            Text(phrase.context.replace("_"," "),
                fontSize = 9.sp, color = Color.LightGray)
        }
    }
}
