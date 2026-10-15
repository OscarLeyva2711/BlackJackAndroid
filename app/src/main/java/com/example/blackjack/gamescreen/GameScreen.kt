package com.example.blackjack.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.blackjack.CardBack
import com.example.blackjack.PlayingCard
import com.example.blackjack.data.ScoreStorage
import com.example.blackjack.logic.BlackjackGame
import com.example.blackjack.logic.Card
import com.example.blackjack.logic.GameState
import com.example.blackjack.logic.Phase
import com.example.blackjack.logic.Outcome
import com.example.blackjack.logic.Reason
import com.example.blackjack.logic.toRecord


const val MIN_TARGET = 11
const val MAX_TARGET = 50

private data class GameConfig(val playerName: String, val target: Int)

/** Contenedor: primero la configuración (número objetivo), luego la partida. */
@Composable
fun GameScreen(storage: ScoreStorage, onExit: () -> Unit) {
    BackHandler(onBack = onExit)
    var config by remember { mutableStateOf<GameConfig?>(null) }

    val current = config
    if (current == null) {
        SetupContent(
            initialName = storage.playerName,
            initialTarget = storage.lastTarget,
            onBack = onExit,
            onStart = { name, target ->
                storage.playerName = name
                storage.lastTarget = target
                config = GameConfig(name, target)
            }
        )
    } else {
        PlayContent(
            config = current,
            storage = storage,
            onNewConfig = { config = null },
            onMenu = onExit
        )
    }
}

// ============================================================
// Configuración previa a la partida
// ============================================================

@Composable
private fun SetupContent(
    initialName: String,
    initialTarget: Int,
    onBack: () -> Unit,
    onStart: (String, Int) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var targetText by remember { mutableStateOf(initialTarget.toString()) }
    val target = targetText.toIntOrNull()
    val valid = target != null && target in MIN_TARGET..MAX_TARGET

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(Modifier.fillMaxWidth()) { TextButton(onClick = onBack) { Text("← Menú") } }
        Spacer(Modifier.weight(1f))

        Text("Nueva partida", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it.take(20) },
            label = { Text("Tu nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = targetText,
            onValueChange = { targetText = it.filter(Char::isDigit).take(2) },
            label = { Text("Número objetivo") },
            singleLine = true,
            isError = !valid,
            supportingText = {
                Text(
                    if (valid) "Gana quien se acerque más a $target sin pasarse"
                    else "Ingresa un número entre $MIN_TARGET y $MAX_TARGET"
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(11, 21, 25).forEach { preset ->
                if (target == preset) {
                    Button(onClick = { targetText = preset.toString() }, modifier = Modifier.weight(1f)) {
                        Text("$preset")
                    }
                } else {
                    OutlinedButton(onClick = { targetText = preset.toString() }, modifier = Modifier.weight(1f)) {
                        Text("$preset")
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = { onStart(name.trim().ifBlank { "Jugador" }, target!!) },
            enabled = valid,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) { Text("Comenzar", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
    }
}

// ============================================================
// Partida
// ============================================================

@Composable
private fun PlayContent(
    config: GameConfig,
    storage: ScoreStorage,
    onNewConfig: () -> Unit,
    onMenu: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val game = remember(config) { BlackjackGame(config.target) }
    var state by remember(config) { mutableStateOf(game.startRound()) }

    // Al terminar la ronda (y si hay ganador) se guarda en TinyDB.
    LaunchedEffect(state) {
        state.toRecord(config.playerName)?.let { storage.addRecord(it) }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onMenu) { Text("← Menú") }
            Spacer(Modifier.weight(1f))
            Text(
                "Meta: ${config.target}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = colors.primary
            )
        }
        Spacer(Modifier.height(8.dp))

        // Casa
        val hidden = state.dealerHoleCardHidden
        HandSection(
            title = "Casa",
            scoreText = if (hidden) "${state.visibleDealerScore} + ?" else "${state.dealerScore}",
            busted = !hidden && state.dealerScore > config.target,
            cards = state.visibleDealerHand,
            showBack = hidden
        )

        Spacer(Modifier.weight(1f))

        // Jugador
        HandSection(
            title = config.playerName,
            scoreText = "${state.playerScore}",
            busted = state.playerScore > config.target,
            cards = state.playerHand,
            showBack = false
        )

        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { state = game.stand() },
                enabled = state.canStand,
                modifier = Modifier.weight(1f).height(56.dp)
            ) { Text("Plantarse", fontSize = 16.sp) }
            Button(
                onClick = { state = game.hit() },
                enabled = state.canHit,
                modifier = Modifier.weight(1f).height(56.dp)
            ) { Text("Pedir carta", fontSize = 16.sp, fontWeight = FontWeight.Bold) }
        }
    }

    if (state.phase == Phase.FINISHED) {
        ResultDialog(
            state = state,
            playerName = config.playerName,
            onSameConfig = { state = game.startRound() },
            onNewConfig = onNewConfig,
            onMenu = onMenu
        )
    }
}

@Composable
private fun HandSection(
    title: String,
    scoreText: String,
    busted: Boolean,
    cards: List<Card>,
    showBack: Boolean
) {
    val colors = MaterialTheme.colorScheme
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            Text(
                title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                scoreText + if (busted) " · ¡Se pasó!" else "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (busted) colors.error else colors.primary
            )
        }
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(cards) { PlayingCard(it) }
            if (showBack) item { CardBack() }
        }
    }
}

// ============================================================
// Mensaje final con 3 opciones
// ============================================================

@Composable
private fun ResultDialog(
    state: GameState,
    playerName: String,
    onSameConfig: () -> Unit,
    onNewConfig: () -> Unit,
    onMenu: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    val (emoji, title) = when (state.outcome) {
        Outcome.PLAYER_WINS -> "🏆" to "¡Ganaste, $playerName!"
        Outcome.DEALER_WINS -> ":(" to "Gana la casa"
        else -> "._." to "Empate"
    }
    val detail = when (state.reason) {
        Reason.PLAYER_BUST -> "Te pasaste: ${state.playerScore} (meta ${state.target})."
        Reason.DEALER_BUST -> "La casa se pasó con ${state.dealerScore}."
        Reason.HIGHER_SCORE -> "Tú: ${state.playerScore} · Casa: ${state.dealerScore}"
        Reason.TIE -> "Ambos con ${state.playerScore}."
        null -> ""
    }

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = colors.surface,
            contentColor = colors.onSurface,
            tonalElevation = 6.dp
        ) {
            Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(emoji, fontSize = 48.sp)
                Spacer(Modifier.height(8.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(detail, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                Spacer(Modifier.height(24.dp))

                Button(onClick = onSameConfig, modifier = Modifier.fillMaxWidth()) {
                    Text("Jugar de nuevo (misma configuración)", textAlign = TextAlign.Center)
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onNewConfig, modifier = Modifier.fillMaxWidth()) {
                    Text("Cambiar número y jugar")
                }
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = onMenu, modifier = Modifier.fillMaxWidth()) {
                    Text("Volver al menú inicial")
                }
            }
        }
    }
}