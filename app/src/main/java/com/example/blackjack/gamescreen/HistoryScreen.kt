package com.example.blackjack

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.blackjack.data.ScoreStorage
import com.example.blackjack.logic.GameRecord


@Composable
fun HistoryScreen(storage: ScoreStorage, onBack: () -> Unit) {
    BackHandler(onBack = onBack)

    val colors = MaterialTheme.colorScheme
    var records by remember { mutableStateOf(storage.getRecords()) }
    var confirmClear by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("← Menú") }
            Spacer(Modifier.weight(1f))
            if (records.isNotEmpty()) {
                TextButton(onClick = { confirmClear = true }) { Text("Borrar", color = colors.error) }
            }
        }
        Text("Historial", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        if (records.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "Aún no hay partidas registradas.\n¡Juega tu primera mano!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(records) { RecordCard(it) }
            }
        }
    }

    if (confirmClear) {
        AlertDialog(
            onDismissRequest = { confirmClear = false },
            containerColor = colors.surface,
            titleContentColor = colors.onSurface,
            textContentColor = colors.onSurface,
            title = { Text("¿Borrar historial?") },
            text = { Text("Se eliminarán todas las partidas guardadas.") },
            confirmButton = {
                TextButton(onClick = {
                    storage.clearRecords()
                    records = emptyList()
                    confirmClear = false
                }) { Text("Borrar", color = colors.error) }
            },
            dismissButton = { TextButton(onClick = { confirmClear = false }) { Text("Cancelar") } }
        )
    }
}

@Composable
private fun RecordCard(r: GameRecord) {
    val colors = MaterialTheme.colorScheme
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface, contentColor = colors.onSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            // Ganador (grande, color de acento) vs Perdedor (apagado)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(
                    Modifier
                        .weight(1.3f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.primary)
                        .padding(12.dp)
                ) {
                    Text(" GANADOR", style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold, color = colors.onPrimary)
                    Text(r.winner, style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold, color = colors.onPrimary,
                        maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Text("vs", Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Bold)
                Column(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.surfaceVariant)
                        .padding(12.dp)
                ) {
                    Text("PERDEDOR", style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold, color = colors.error)
                    Text(r.loser, style = MaterialTheme.typography.titleMedium,
                        color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }

            Spacer(Modifier.height(12.dp))
            Text(" ${r.formattedDate}", style = MaterialTheme.typography.bodyMedium)
            Text("Meta ${r.target} · Ganó con ${r.winningScore}", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(10.dp))
            Text("Combinación ganadora", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                r.winningCards.forEach { MiniCard(it) }
            }
        }
    }
}