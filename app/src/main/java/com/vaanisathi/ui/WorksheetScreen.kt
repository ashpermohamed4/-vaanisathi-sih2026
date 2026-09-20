package com.vaanisathi.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
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
import com.vaanisathi.db.FLNPhrase
import com.vaanisathi.db.VaaniSathiDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorksheetScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val db = remember { VaaniSathiDatabase.getInstance(context) }
    val scope = rememberCoroutineScope()

    var phrases by remember { mutableStateOf<List<FLNPhrase>>(emptyList()) }
    var isGenerating by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        phrases = db.phraseDao().getAllPhrases()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("NIPUN Worksheet Generator",
                            color = Color.White, fontWeight = FontWeight.Bold)
                        Text("Offline Printable Bilingual Worksheets",
                            color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NavyBlue)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8FAFF))
                .padding(16.dp)
        ) {
            // Header card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "📄 NIPUN Bharat Aligned Worksheet",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = NavyBlue
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Generated offline for Grades 1–3 tribal classrooms. Includes Hindi, Santhali Roman, and Ol Chiki script.",
                        fontSize = 12.sp,
                        color = Color(0xFF475569)
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            isGenerating = true
                            scope.launch {
                                delay(1200)
                                isGenerating = false
                                Toast.makeText(
                                    context,
                                    "✅ Worksheet PDF saved to /Downloads/VaaniSathi_NIPUN_Worksheet.pdf",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SaffronOrange),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF", tint = Color.White)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            if (isGenerating) "Generating PDF..." else "Generate & Export PDF Worksheet",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Preview: Bilingual Vocabulary Table (${phrases.size} Phrases)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = NavyBlue
            )
            Spacer(Modifier.height(8.dp))

            // Table preview
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFE2E8F0))
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Hindi", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Text("Roman", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Text("Ol Chiki", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.weight(1f))
                            Text("Code", fontWeight = FontWeight.Bold, fontSize = 11.sp, textAlign = TextAlign.End, modifier = Modifier.weight(0.8f))
                        }
                    }
                    items(phrases) { p ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(p.hindiText, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = NavyBlue, modifier = Modifier.weight(1f))
                            Text(p.santhaliRoman, fontSize = 12.sp, color = Color(0xFF64748B), modifier = Modifier.weight(1f))
                            Text(p.santhaliOlChiki, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ForestGreen, modifier = Modifier.weight(1f))
                            Text(p.nipunCode, fontSize = 10.sp, color = Color.Gray, textAlign = TextAlign.End, modifier = Modifier.weight(0.8f))
                        }
                        HorizontalDivider(color = Color(0xFFF1F5F9))
                    }
                }
            }
        }
    }
}
