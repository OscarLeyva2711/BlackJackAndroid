package com.example.blackjack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.blackjack.data.ScoreStorage
import com.example.blackjack.ui.AppTheme
import com.example.blackjack.ui.BlackjackTheme
import com.example.blackjack.ui.GameScreen




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val storage = ScoreStorage(this)
        setContent { BlackjackApp(storage) }
    }
}

private enum class Screen { MENU, HISTORY, GAME }

@Composable
fun BlackjackApp(storage: ScoreStorage) {
    var screen by remember { mutableStateOf(Screen.MENU) }
    var themeIndex by remember {
        mutableIntStateOf(storage.themeIndex.coerceIn(0, AppTheme.entries.lastIndex))
    }

    BlackjackTheme(AppTheme.entries[themeIndex]) {
        val colors = MaterialTheme.colorScheme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colors.background,
            contentColor = colors.onBackground
        ) {
            Box(Modifier.fillMaxSize().safeDrawingPadding()) {
                when (screen) {
                    Screen.MENU -> MenuScreen(
                        selectedTheme = themeIndex,
                        onThemeSelected = { themeIndex = it; storage.themeIndex = it },
                        onPlay = { screen = Screen.GAME },
                        onHistory = { screen = Screen.HISTORY }
                    )
                    Screen.HISTORY -> HistoryScreen(storage, onBack = { screen = Screen.MENU })
                    Screen.GAME -> GameScreen(storage, onExit = { screen = Screen.MENU })
                }
            }
        }
    }
}