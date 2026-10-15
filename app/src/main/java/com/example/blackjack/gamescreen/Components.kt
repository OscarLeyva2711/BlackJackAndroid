package com.example.blackjack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.blackjack.logic.Card
import com.example.blackjack.logic.Suit


private val CardRed = Color(0xFFC62828)
private val CardBlack = Color(0xFF212121)
private val CardBorder = Color(0xFFBDBDBD)

@Composable
fun PlayingCard(card: Card, modifier: Modifier = Modifier) {
    val color = if (card.suit == Suit.CORAZONES || card.suit == Suit.DIAMANTES) CardRed else CardBlack
    val shape = RoundedCornerShape(10.dp)
    Column(
        modifier = modifier
            .size(76.dp, 110.dp)
            .shadow(4.dp, shape)
            .background(Color.White, shape)
            .border(1.dp, CardBorder, shape)
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(card.rank.label, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(
            card.suit.symbol, color = color, fontSize = 32.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            card.rank.label, color = color, fontSize = 20.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

/** Carta boca abajo (la segunda carta del crupier durante el turno del jugador). */
@Composable
fun CardBack(modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .size(76.dp, 110.dp)
            .shadow(4.dp, shape)
            .background(MaterialTheme.colorScheme.primary, shape)
            .border(3.dp, Color.White, shape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "?", fontSize = 36.sp, fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

/** Carta pequeña (texto tipo "A♠") para la combinacion ganadora del historial. */
@Composable
fun MiniCard(text: String) {
    val red = '♥' in text || '♦' in text
    val shape = RoundedCornerShape(6.dp)
    Box(
        Modifier
            .background(Color.White, shape)
            .border(1.dp, CardBorder, shape)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Text(text, color = if (red) CardRed else CardBlack, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}