package com.vaanisathi.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaanisathi.db.FLNPhrase

@Composable
fun PhraseCardScreen(
    phrases: List<FLNPhrase>,
    onPhraseSelected: (FLNPhrase) -> Unit,
    onPlayAudio: (String?) -> Unit
) {
    var selectedDomain by remember { mutableStateOf("all") }

    val domains = listOf(
        "all" to "All (ᱡᱚᱛᱚ)",
        "oral_language" to "Oral (ᱨᱚᱲ)",
        "numeracy" to "Numeracy (ᱮᱞ)",
        "print_awareness" to "Print (ᱪᱤᱠᱤ)",
        "reading" to "Reading (ᱯᱟᱲᱦᱟᱣ)"
    )

    val filteredPhrases = if (selectedDomain == "all") {
        phrases
    } else {
        phrases.filter { it.domain == selectedDomain }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F4F8))
            .padding(16.dp)
    ) {
        Text(
            text = "Student Phrase Cards (ᱵᱤᱫᱽᱭᱟᱨᱛᱷᱤ ᱠᱟᱨᱰ)",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B)
        )
        Text(
            text = "Tap any card for instant bilingual speech & reverse translation (<5ms)",
            fontSize = 12.sp,
            color = Color(0xFF64748B)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Domain Filter Tabs
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(domains) { (key, title) ->
                FilterChip(
                    selected = selectedDomain == key,
                    onClick = { selectedDomain = key },
                    label = { Text(title, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF1976D2),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Phrase Cards Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredPhrases) { phrase ->
                PhraseItemCard(
                    phrase = phrase,
                    onClick = {
                        onPhraseSelected(phrase)
                        onPlayAudio(phrase.audioFilename)
                    }
                )
            }
        }
    }
}

@Composable
fun PhraseItemCard(
    phrase: FLNPhrase,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFE2E8F0)
                ) {
                    Text(
                        text = phrase.phraseCode,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF475569),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                IconButton(
                    onClick = onClick,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Play",
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Santhali Ol Chiki Text
            Text(
                text = phrase.santhaliOlChiki,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A),
                textAlign = TextAlign.Center
            )

            // Romanized Santhali
            Text(
                text = phrase.santhaliRoman,
                fontSize = 13.sp,
                color = Color(0xFF059669),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))
            Divider(color = Color(0xFFF1F5F9))
            Spacer(modifier = Modifier.height(6.dp))

            // Hindi Meaning
            Text(
                text = phrase.hindiText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF334155),
                textAlign = TextAlign.Center
            )
        }
    }
}
