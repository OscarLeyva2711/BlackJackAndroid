package com.example.blackjack.logic

import kotlin.random.Random

enum class Suit(val symbol: String) {
    CORAZONES("♥"), DIAMANTES("♦"), TREBOLES("♣"), PICAS("♠")
}

enum class Rank(val label: String, val value: Int) {
    AS("A", 1), DOS("2", 2), TRES("3", 3), CUATRO("4", 4), CINCO("5", 5),
    SEIS("6", 6), SIETE("7", 7), OCHO("8", 8), NUEVE("9", 9), DIEZ("10", 10),
    J("J", 10), Q("Q", 10), K("K", 10)
}

data class Card(val rank: Rank, val suit: Suit) {
    override fun toString() = "${rank.label}${suit.symbol}"
}

/** Baraja inglesa de 52 cartas. Si se acaba, se vuelve a barajar. */
class Deck(private val random: Random = Random.Default) {
    private val cards = ArrayDeque<Card>()

    init { reset() }

    fun reset() {
        cards.clear()
        val fresh = Suit.entries.flatMap { suit -> Rank.entries.map { Card(it, suit) } }
        cards.addAll(fresh.shuffled(random))
    }

    fun draw(): Card {
        if (cards.isEmpty()) reset()
        return cards.removeFirst()
    }
}