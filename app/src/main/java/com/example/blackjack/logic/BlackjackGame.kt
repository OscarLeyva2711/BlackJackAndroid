package com.example.blackjack.logic

import mx.uabc.blackjack.logic.ScoreCalculator
import kotlin.random.Random

enum class Phase { PLAYER_TURN, FINISHED }
enum class Outcome { PLAYER_WINS, DEALER_WINS, TIE }
enum class Reason { PLAYER_BUST, DEALER_BUST, HIGHER_SCORE, TIE }

data class GameState(
    val target: Int,
    val playerHand: List<Card>,
    val dealerHand: List<Card>,
    val phase: Phase,
    val outcome: Outcome? = null,
    val reason: Reason? = null
) {
    val playerScore: Int get() = ScoreCalculator.score(playerHand, target).total
    val dealerScore: Int get() = ScoreCalculator.score(dealerHand, target).total

    /** Mientras es turno del jugador, la segunda carta de la casa va boca abajo. */
    val dealerHoleCardHidden: Boolean get() = phase == Phase.PLAYER_TURN
    val visibleDealerHand: List<Card>
        get() = if (dealerHoleCardHidden) dealerHand.take(1) else dealerHand
    val visibleDealerScore: Int
        get() = ScoreCalculator.score(visibleDealerHand, target).total

    val canHit: Boolean get() = phase == Phase.PLAYER_TURN
    val canStand: Boolean get() = phase == Phase.PLAYER_TURN
}

class BlackjackGame(
    val target: Int = 21,
    random: Random = Random.Default
) {
    private val deck = Deck(random)

    var state: GameState = emptyState()
        private set

    private fun emptyState() = GameState(target, emptyList(), emptyList(), Phase.FINISHED)

    /** Reparte 2 cartas a cada uno e inicia la ronda. */
    fun startRound(): GameState {
        deck.reset()
        state = GameState(
            target = target,
            playerHand = listOf(deck.draw(), deck.draw()),
            dealerHand = listOf(deck.draw(), deck.draw()),
            phase = Phase.PLAYER_TURN
        )
        return settlePlayerIfNeeded()
    }

    /** El jugador pide carta. */
    fun hit(): GameState {
        check(state.phase == Phase.PLAYER_TURN) { "La ronda ya terminó" }
        state = state.copy(playerHand = state.playerHand + deck.draw())
        return settlePlayerIfNeeded()
    }

    /** El jugador se planta: juega la casar y se resuelve la ronda. */
    fun stand(): GameState {
        check(state.phase == Phase.PLAYER_TURN) { "La ronda ya terminó" }
        state = playDealerAndResolve()
        return state
    }

    // reglas internas

    private fun settlePlayerIfNeeded(): GameState {
        val score = state.playerScore
        state = when {
            score > target -> state.copy(
                phase = Phase.FINISHED,
                outcome = Outcome.DEALER_WINS,
                reason = Reason.PLAYER_BUST
            )
            score == target -> playDealerAndResolve()   // llegó al objetivo: se planta solo
            else -> state
        }
        return state
    }

    private fun playDealerAndResolve(): GameState {
        val threshold = ScoreCalculator.dealerThreshold(target)
        var dealer = state.dealerHand
        while (ScoreCalculator.score(dealer, target).total < threshold) {
            dealer = dealer + deck.draw()
        }
        val dealerScore = ScoreCalculator.score(dealer, target).total
        val playerScore = state.playerScore

        val (outcome, reason) = when {
            dealerScore > target -> Outcome.PLAYER_WINS to Reason.DEALER_BUST
            playerScore > dealerScore -> Outcome.PLAYER_WINS to Reason.HIGHER_SCORE
            dealerScore > playerScore -> Outcome.DEALER_WINS to Reason.HIGHER_SCORE
            else -> Outcome.TIE to Reason.TIE
        }
        return state.copy(
            dealerHand = dealer,
            phase = Phase.FINISHED,
            outcome = outcome,
            reason = reason
        )
    }
}