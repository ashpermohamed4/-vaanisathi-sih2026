package com.vaanisathi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.vaanisathi.asr.VoskASREngine
import com.vaanisathi.db.FLNLesson
import com.vaanisathi.db.FLNPhrase
import com.vaanisathi.db.VaaniSathiDatabase
import com.vaanisathi.nmt.IndicTransEngine
import com.vaanisathi.tts.AudioPlayer
import com.vaanisathi.ui.LessonScreen
import com.vaanisathi.ui.PhraseCardScreen
import com.vaanisathi.ui.TeacherScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var asrEngine: VoskASREngine
    private lateinit var audioPlayer: AudioPlayer
    private lateinit var database: VaaniSathiDatabase
    private lateinit var nmtEngine: IndicTransEngine

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            asrEngine.initializeModel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Engines & Offline Database
        asrEngine = VoskASREngine(this)
        audioPlayer = AudioPlayer(this)
        database = VaaniSathiDatabase.getDatabase(this)
        nmtEngine = IndicTransEngine(this)

        checkAndRequestAudioPermission()

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF1976D2),
                    secondary = Color(0xFF2E7D32),
                    background = Color(0xFFF8F9FA)
                )
            ) {
                VaaniSathiApp(
                    asrEngine = asrEngine,
                    audioPlayer = audioPlayer,
                    database = database,
                    nmtEngine = nmtEngine
                )
            }
        }
    }

    private fun checkAndRequestAudioPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            asrEngine.initializeModel()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        asrEngine.stopListening()
        audioPlayer.release()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaaniSathiApp(
    asrEngine: VoskASREngine,
    audioPlayer: AudioPlayer,
    database: VaaniSathiDatabase,
    nmtEngine: IndicTransEngine
) {
    var currentTab by remember { mutableStateOf(0) }

    val isListening by asrEngine.isListening.collectAsState()
    val recognizedText by asrEngine.recognizedText.collectAsState()
    val latencyMs by asrEngine.lastLatencyMs.collectAsState()
    val engineStatus by asrEngine.engineStatus.collectAsState()

    var matchedPhrase by remember { mutableStateOf<FLNPhrase?>(null) }
    var tier2Translation by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val phrasesList by database.phraseDao().getAllPhrases().collectAsState(initial = emptyList())
    val lessonsList by database.phraseDao().getLessonsByGrade(1).collectAsState(initial = emptyList())

    // React to live speech recognition output
    LaunchedEffect(recognizedText) {
        if (recognizedText.isNotBlank()) {
            withContext(Dispatchers.IO) {
                val exact = database.phraseDao().findExactMatch(recognizedText.trim())
                if (exact != null) {
                    matchedPhrase = exact
                    tier2Translation = null
                } else {
                    val fuzzy = database.phraseDao().findFuzzyMatches(recognizedText.trim())
                    if (fuzzy.isNotEmpty()) {
                        matchedPhrase = fuzzy.first()
                        tier2Translation = null
                    } else {
                        // Tier 2 Fallback
                        matchedPhrase = null
                        val result = nmtEngine.translateHindiToSanthali(recognizedText)
                        tier2Translation = "${result.olChikiText} (${result.romanText})"
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "VaaniSathi (वाणीसाथी)",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1976D2),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 },
                    icon = { Icon(Icons.Default.Mic, contentDescription = "Teacher") },
                    label = { Text("Teacher Live") }
                )
                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 },
                    icon = { Icon(Icons.Default.ViewModule, contentDescription = "Phrases") },
                    label = { Text("Phrase Cards") }
                )
                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 },
                    icon = { Icon(Icons.Default.AutoStories, contentDescription = "Lessons") },
                    label = { Text("FLN Lessons") }
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (currentTab) {
                0 -> TeacherScreen(
                    isListening = isListening,
                    recognizedText = recognizedText,
                    matchedPhrase = matchedPhrase,
                    tier2Translation = tier2Translation,
                    latencyMs = latencyMs,
                    engineStatus = engineStatus,
                    onToggleListen = {
                        if (isListening) asrEngine.stopListening() else asrEngine.startListening()
                    },
                    onPlayAudio = { audioFilename ->
                        audioPlayer.playAssetAudio(audioFilename)
                    }
                )
                1 -> PhraseCardScreen(
                    phrases = phrasesList,
                    onPhraseSelected = { phrase ->
                        matchedPhrase = phrase
                    },
                    onPlayAudio = { audioFilename ->
                        audioPlayer.playAssetAudio(audioFilename)
                    }
                )
                2 -> LessonScreen(
                    lessons = lessonsList,
                    onSelectLesson = { lesson ->
                        // Start lesson workflow
                    }
                )
            }
        }
    }
}
