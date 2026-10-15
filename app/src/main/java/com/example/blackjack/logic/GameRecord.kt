package com.example.blackjack.logic

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class GameRecord(
    val winner: String,
    val loser: String,
    val timestamp: Long,
    val winningCards: List<String>,
    val winningScore: Int,
    val target: Int
) {
    val formattedDate: String
        get() = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "MX")).format(Date(timestamp))
    val winningCombination: String get() = winningCards.joinToString(" ")
}

/** Devuelve null en empate (no hay ganador que registrar). */
fun GameState.toRecord(
    playerName: String,
    dealerName: String = "Casa",
    now: Long = System.currentTimeMillis()
): GameRecord? {
    if (phase != Phase.FINISHED) return null
    return when (outcome) {
        Outcome.PLAYER_WINS -> GameRecord(
            playerName, dealerName, now, playerHand.map { it.toString() }, playerScore, target
        )
        Outcome.DEALER_WINS -> GameRecord(
            dealerName, playerName, now, dealerHand.map { it.toString() }, dealerScore, target
        )
        else -> null
    }
}