package com.vaanisathi.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vaanisathi.db.FLNPhrase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherScreen(
    isListening: Boolean,
    recognizedText: String,
    matchedPhrase: FLNPhrase?,
    tier2Translation: String?,
    latencyMs: Long,
    engineStatus: String,
    onToggleListen: () -> Unit,
    onPlayAudio: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Status & Offline Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFE8F5E9)
            ) {
                Text(
                    text = "● 100% OFFLINE (0 API Calls)",
                    color = Color(0xFF2E7D32),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            if (latencyMs > 0) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (latencyMs <= 3000) Color(0xFFE3F2FD) else Color(0xFFFFEBEE)
                ) {
                    Text(
                        text = "⚡ Latency: ${latencyMs}ms",
                        color = if (latencyMs <= 3000) Color(0xFF1565C0) else Color(0xFFC62828),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Engine Status Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Text(
                text = engineStatus,
                color = Color(0xFF555555),
                fontSize = 13.sp,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Microphone Action Button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(140.dp)
        ) {
            IconButton(
                onClick = onToggleListen,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(if (isListening) Color(0xFFD32F2F) else Color(0xFF1976D2))
            ) {
                Icon(
                    imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = "Microphone",
                    tint = Color.White,
                    modifier = Modifier.size(54.dp)
                )
            }
        }

        Text(
            text = if (isListening) "Listening to Teacher (Hindi)..." else "Tap to Speak (Hindi)",
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = if (isListening) Color(0xFFD32F2F) else Color(0xFF333333)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Recognized Hindi Text Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDE7F6))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "TEACHER SPEECH (HINDI)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5E35B1)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (recognizedText.isNotBlank()) recognizedText else "बोलना शुरू करने के लिए माइक दबाएं...",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF212121)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Result Card (Tier 1 Match or Tier 2 Translation)
        AnimatedVisibility(visible = matchedPhrase != null || tier2Translation != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (matchedPhrase != null) Color(0xFF2E7D32) else Color(0xFFE65100)
                        ) {
                            Text(
                                text = if (matchedPhrase != null) "TIER 1 MATCH (<5ms)" else "TIER 2 NMT EXTENDED",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }

                        if (matchedPhrase?.nipunCode != null) {
                            Text(
                                text = "NIPUN: ${matchedPhrase.nipunCode}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Santhali Ol Chiki Display
                    Text(
                        text = matchedPhrase?.santhaliOlChiki ?: tier2Translation ?: "",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20),
                        textAlign = TextAlign.Center
                    )

                    // Santhali Romanized Fallback
                    if (matchedPhrase?.santhaliRoman != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "(${matchedPhrase.santhaliRoman})",
                            fontSize = 16.sp,
                            color = Color(0xFF388E3C),
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Audio Playback Button
                    Button(
                        onClick = { onPlayAudio(matchedPhrase?.audioFilename) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "Play Audio", tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Play Native Santhali Audio", color = Color.White)
                    }
                }
            }
        }
    }
}
