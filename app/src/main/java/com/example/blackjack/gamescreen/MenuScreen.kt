package com.example.blackjack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackjack.ui.AppTheme

@Composable
fun MenuScreen(
    selectedTheme: Int,
    onThemeSelected: (Int) -> Unit,
    onPlay: () -> Unit,
    onHistory: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("♠ ♥ ♣ ♦", fontSize = 28.sp, color = colors.secondary)
        Text("21", fontSize = 96.sp, fontWeight = FontWeight.Black, color = colors.primary)
        Text("BlackJack", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Spacer(Modifier.height(40.dp))

        Button(onClick = onPlay, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Jugar", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Historial de puntuaciones", fontSize = 18.sp)
        }

        Spacer(Modifier.height(36.dp))

        Text("Tema", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AppTheme.available.forEachIndexed { index, theme ->
                ThemeSwatch(theme, selected = index == selectedTheme) { onThemeSelected(index) }
            }
        }
    }
}

@Composable
private fun ThemeSwatch(theme: AppTheme, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val scheme = theme.resolveScheme()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(scheme.primaryContainer)
                .border(
                    width = if (selected) 3.dp else 1.dp,
                    color = if (selected) colors.primary else colors.outline,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.size(22.dp).clip(CircleShape).background(scheme.primary))
        }
        Spacer(Modifier.height(4.dp))
        Text(
            theme.displayName,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}