package mx.uabc.blackjack.logic

import com.example.blackjack.logic.Card
import com.example.blackjack.logic.Rank

data class HandScore(val total: Int, val isSoft: Boolean)

object ScoreCalculator {

    /**
     * El As vale 1, o 11 si con eso no se pasa del objetivo.
     * (Solo un As puede valer 11; dos valdrían 22 y siempre se pasarían.)
     */
    fun score(hand: List<Card>, target: Int): HandScore {
        var total = hand.sumOf { it.rank.value }
        val hasAce = hand.any { it.rank == Rank.AS }
        var soft = false
        if (hasAce && total + 10 <= target) {
            total += 10
            soft = true
        }
        return HandScore(total, soft)
    }

    /** Umbral de la casa: pide carta mientras tenga menos que esto (21 -> 17, 25 -> 21, 11 -> 7). */
    fun dealerThreshold(target: Int): Int = (target - 4).coerceAtLeast(1)
}