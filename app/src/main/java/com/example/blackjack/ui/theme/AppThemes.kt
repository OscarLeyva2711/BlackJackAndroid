package com.example.blackjack.ui

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Paletas Material 3 (generadas con Material Color Utilities a partir de un color semilla,
 * igual que Material Theme Builder). Cada tema tiene variante clara y oscura; se usa la
 * que corresponda al modo del sistema. DINAMICO usa Material You (Android 12+).
 */
enum class AppTheme(
    val displayName: String,
    private val light: ColorScheme?,
    private val dark: ColorScheme?
) {
    VIOLETA(
        displayName = "Violeta",
        light = lightColorScheme(
            primary = Color(0xFF65558F), onPrimary = Color(0xFFFFFFFF), primaryContainer = Color(0xFFE9DDFF), onPrimaryContainer = Color(0xFF4D3D75), inversePrimary = Color(0xFFCFBDFE),
            secondary = Color(0xFF625B71), onSecondary = Color(0xFFFFFFFF), secondaryContainer = Color(0xFFE8DEF8), onSecondaryContainer = Color(0xFF4A4458),
            tertiary = Color(0xFF7E5260), onTertiary = Color(0xFFFFFFFF), tertiaryContainer = Color(0xFFFFD9E3), onTertiaryContainer = Color(0xFF633B48),
            background = Color(0xFFFDF7FF), onBackground = Color(0xFF1D1B20), surface = Color(0xFFFDF7FF), onSurface = Color(0xFF1D1B20), surfaceVariant = Color(0xFFE7E0EB), onSurfaceVariant = Color(0xFF49454E), surfaceTint = Color(0xFF65558F),
            inverseSurface = Color(0xFF322F35), inverseOnSurface = Color(0xFFF5EFF7),
            error = Color(0xFFBA1A1A), onError = Color(0xFFFFFFFF), errorContainer = Color(0xFFFFDAD6), onErrorContainer = Color(0xFF93000A),
            outline = Color(0xFF7A757F), outlineVariant = Color(0xFFCAC4CF), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFFFDF7FF), surfaceDim = Color(0xFFDED8E0), surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFF8F2FA), surfaceContainer = Color(0xFFF2ECF4), surfaceContainerHigh = Color(0xFFECE6EE), surfaceContainerHighest = Color(0xFFE6E0E9)
        ),
        dark = darkColorScheme(
            primary = Color(0xFFCFBDFE), onPrimary = Color(0xFF36275D), primaryContainer = Color(0xFF4D3D75), onPrimaryContainer = Color(0xFFE9DDFF), inversePrimary = Color(0xFF65558F),
            secondary = Color(0xFFCBC2DB), onSecondary = Color(0xFF332D41), secondaryContainer = Color(0xFF4A4458), onSecondaryContainer = Color(0xFFE8DEF8),
            tertiary = Color(0xFFEFB8C8), onTertiary = Color(0xFF4A2532), tertiaryContainer = Color(0xFF633B48), onTertiaryContainer = Color(0xFFFFD9E3),
            background = Color(0xFF141218), onBackground = Color(0xFFE6E0E9), surface = Color(0xFF141218), onSurface = Color(0xFFE6E0E9), surfaceVariant = Color(0xFF49454E), onSurfaceVariant = Color(0xFFCAC4CF), surfaceTint = Color(0xFFCFBDFE),
            inverseSurface = Color(0xFFE6E0E9), inverseOnSurface = Color(0xFF322F35),
            error = Color(0xFFFFB4AB), onError = Color(0xFF690005), errorContainer = Color(0xFF93000A), onErrorContainer = Color(0xFFFFDAD6),
            outline = Color(0xFF948F99), outlineVariant = Color(0xFF49454E), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFF3B383E), surfaceDim = Color(0xFF141218), surfaceContainerLowest = Color(0xFF0F0D13), surfaceContainerLow = Color(0xFF1D1B20), surfaceContainer = Color(0xFF211F24), surfaceContainerHigh = Color(0xFF2B292F), surfaceContainerHighest = Color(0xFF36343A)
        )
    ),

    AZUL(
        displayName = "Azul",
        light = lightColorScheme(
            primary = Color(0xFF36618E), onPrimary = Color(0xFFFFFFFF), primaryContainer = Color(0xFFD1E4FF), onPrimaryContainer = Color(0xFF1A4975), inversePrimary = Color(0xFFA0CAFD),
            secondary = Color(0xFF535F70), onSecondary = Color(0xFFFFFFFF), secondaryContainer = Color(0xFFD7E3F8), onSecondaryContainer = Color(0xFF3B4858),
            tertiary = Color(0xFF6B5778), onTertiary = Color(0xFFFFFFFF), tertiaryContainer = Color(0xFFF3DAFF), onTertiaryContainer = Color(0xFF523F5F),
            background = Color(0xFFF8F9FF), onBackground = Color(0xFF191C20), surface = Color(0xFFF8F9FF), onSurface = Color(0xFF191C20), surfaceVariant = Color(0xFFDFE2EB), onSurfaceVariant = Color(0xFF43474E), surfaceTint = Color(0xFF36618E),
            inverseSurface = Color(0xFF2E3135), inverseOnSurface = Color(0xFFEFF0F7),
            error = Color(0xFFBA1A1A), onError = Color(0xFFFFFFFF), errorContainer = Color(0xFFFFDAD6), onErrorContainer = Color(0xFF93000A),
            outline = Color(0xFF73777F), outlineVariant = Color(0xFFC3C6CF), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFFF8F9FF), surfaceDim = Color(0xFFD8DAE0), surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFF2F3FA), surfaceContainer = Color(0xFFECEEF4), surfaceContainerHigh = Color(0xFFE6E8EE), surfaceContainerHighest = Color(0xFFE1E2E8)
        ),
        dark = darkColorScheme(
            primary = Color(0xFFA0CAFD), onPrimary = Color(0xFF003258), primaryContainer = Color(0xFF1A4975), onPrimaryContainer = Color(0xFFD1E4FF), inversePrimary = Color(0xFF36618E),
            secondary = Color(0xFFBBC7DB), onSecondary = Color(0xFF253140), secondaryContainer = Color(0xFF3B4858), onSecondaryContainer = Color(0xFFD7E3F8),
            tertiary = Color(0xFFD7BEE4), onTertiary = Color(0xFF3B2948), tertiaryContainer = Color(0xFF523F5F), onTertiaryContainer = Color(0xFFF3DAFF),
            background = Color(0xFF111418), onBackground = Color(0xFFE1E2E8), surface = Color(0xFF111418), onSurface = Color(0xFFE1E2E8), surfaceVariant = Color(0xFF43474E), onSurfaceVariant = Color(0xFFC3C6CF), surfaceTint = Color(0xFFA0CAFD),
            inverseSurface = Color(0xFFE1E2E8), inverseOnSurface = Color(0xFF2E3135),
            error = Color(0xFFFFB4AB), onError = Color(0xFF690005), errorContainer = Color(0xFF93000A), onErrorContainer = Color(0xFFFFDAD6),
            outline = Color(0xFF8D9199), outlineVariant = Color(0xFF43474E), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFF36393E), surfaceDim = Color(0xFF111418), surfaceContainerLowest = Color(0xFF0B0E13), surfaceContainerLow = Color(0xFF191C20), surfaceContainer = Color(0xFF1D2024), surfaceContainerHigh = Color(0xFF272A2F), surfaceContainerHighest = Color(0xFF32353A)
        )
    ),

    VERDE(
        displayName = "Verde",
        light = lightColorScheme(
            primary = Color(0xFF3C6939), onPrimary = Color(0xFFFFFFFF), primaryContainer = Color(0xFFBCF0B4), onPrimaryContainer = Color(0xFF245024), inversePrimary = Color(0xFFA1D39A),
            secondary = Color(0xFF52634F), onSecondary = Color(0xFFFFFFFF), secondaryContainer = Color(0xFFD6E8CE), onSecondaryContainer = Color(0xFF3B4B38),
            tertiary = Color(0xFF38656A), onTertiary = Color(0xFFFFFFFF), tertiaryContainer = Color(0xFFBCEBF0), onTertiaryContainer = Color(0xFF1F4D52),
            background = Color(0xFFF7FBF1), onBackground = Color(0xFF191D17), surface = Color(0xFFF7FBF1), onSurface = Color(0xFF191D17), surfaceVariant = Color(0xFFDEE5D8), onSurfaceVariant = Color(0xFF424940), surfaceTint = Color(0xFF3C6939),
            inverseSurface = Color(0xFF2D322C), inverseOnSurface = Color(0xFFEFF2E9),
            error = Color(0xFFBA1A1A), onError = Color(0xFFFFFFFF), errorContainer = Color(0xFFFFDAD6), onErrorContainer = Color(0xFF93000A),
            outline = Color(0xFF72796F), outlineVariant = Color(0xFFC2C9BD), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFFF7FBF1), surfaceDim = Color(0xFFD8DBD2), surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFF1F5EB), surfaceContainer = Color(0xFFECEFE6), surfaceContainerHigh = Color(0xFFE6E9E0), surfaceContainerHighest = Color(0xFFE0E4DB)
        ),
        dark = darkColorScheme(
            primary = Color(0xFFA1D39A), onPrimary = Color(0xFF0A390F), primaryContainer = Color(0xFF245024), onPrimaryContainer = Color(0xFFBCF0B4), inversePrimary = Color(0xFF3C6939),
            secondary = Color(0xFFBACCB3), onSecondary = Color(0xFF253423), secondaryContainer = Color(0xFF3B4B38), onSecondaryContainer = Color(0xFFD6E8CE),
            tertiary = Color(0xFFA0CFD4), onTertiary = Color(0xFF00363B), tertiaryContainer = Color(0xFF1F4D52), onTertiaryContainer = Color(0xFFBCEBF0),
            background = Color(0xFF10140F), onBackground = Color(0xFFE0E4DB), surface = Color(0xFF10140F), onSurface = Color(0xFFE0E4DB), surfaceVariant = Color(0xFF424940), onSurfaceVariant = Color(0xFFC2C9BD), surfaceTint = Color(0xFFA1D39A),
            inverseSurface = Color(0xFFE0E4DB), inverseOnSurface = Color(0xFF2D322C),
            error = Color(0xFFFFB4AB), onError = Color(0xFF690005), errorContainer = Color(0xFF93000A), onErrorContainer = Color(0xFFFFDAD6),
            outline = Color(0xFF8C9388), outlineVariant = Color(0xFF424940), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFF363A34), surfaceDim = Color(0xFF10140F), surfaceContainerLowest = Color(0xFF0B0F0A), surfaceContainerLow = Color(0xFF191D17), surfaceContainer = Color(0xFF1D211B), surfaceContainerHigh = Color(0xFF272B25), surfaceContainerHighest = Color(0xFF323630)
        )
    ),

    NARANJA(
        displayName = "Naranja",
        light = lightColorScheme(
            primary = Color(0xFF8F4C33), onPrimary = Color(0xFFFFFFFF), primaryContainer = Color(0xFFFFDBCF), onPrimaryContainer = Color(0xFF71361E), inversePrimary = Color(0xFFFFB59A),
            secondary = Color(0xFF77574C), onSecondary = Color(0xFFFFFFFF), secondaryContainer = Color(0xFFFFDBCF), onSecondaryContainer = Color(0xFF5D4035),
            tertiary = Color(0xFF695E2F), onTertiary = Color(0xFFFFFFFF), tertiaryContainer = Color(0xFFF2E3A8), onTertiaryContainer = Color(0xFF50471A),
            background = Color(0xFFFFF8F6), onBackground = Color(0xFF231A16), surface = Color(0xFFFFF8F6), onSurface = Color(0xFF231A16), surfaceVariant = Color(0xFFF5DED6), onSurfaceVariant = Color(0xFF53433E), surfaceTint = Color(0xFF8F4C33),
            inverseSurface = Color(0xFF392E2B), inverseOnSurface = Color(0xFFFFEDE7),
            error = Color(0xFFBA1A1A), onError = Color(0xFFFFFFFF), errorContainer = Color(0xFFFFDAD6), onErrorContainer = Color(0xFF93000A),
            outline = Color(0xFF85736D), outlineVariant = Color(0xFFD8C2BB), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFFFFF8F6), surfaceDim = Color(0xFFE8D6D1), surfaceContainerLowest = Color(0xFFFFFFFF), surfaceContainerLow = Color(0xFFFFF1EC), surfaceContainer = Color(0xFFFCEAE4), surfaceContainerHigh = Color(0xFFF7E4DF), surfaceContainerHighest = Color(0xFFF1DFD9)
        ),
        dark = darkColorScheme(
            primary = Color(0xFFFFB59A), onPrimary = Color(0xFF55200A), primaryContainer = Color(0xFF71361E), onPrimaryContainer = Color(0xFFFFDBCF), inversePrimary = Color(0xFF8F4C33),
            secondary = Color(0xFFE7BEAF), onSecondary = Color(0xFF442A20), secondaryContainer = Color(0xFF5D4035), onSecondaryContainer = Color(0xFFFFDBCF),
            tertiary = Color(0xFFD5C78E), onTertiary = Color(0xFF383005), tertiaryContainer = Color(0xFF50471A), onTertiaryContainer = Color(0xFFF2E3A8),
            background = Color(0xFF1A110E), onBackground = Color(0xFFF1DFD9), surface = Color(0xFF1A110E), onSurface = Color(0xFFF1DFD9), surfaceVariant = Color(0xFF53433E), onSurfaceVariant = Color(0xFFD8C2BB), surfaceTint = Color(0xFFFFB59A),
            inverseSurface = Color(0xFFF1DFD9), inverseOnSurface = Color(0xFF392E2B),
            error = Color(0xFFFFB4AB), onError = Color(0xFF690005), errorContainer = Color(0xFF93000A), onErrorContainer = Color(0xFFFFDAD6),
            outline = Color(0xFFA08D86), outlineVariant = Color(0xFF53433E), scrim = Color(0xFF000000),
            surfaceBright = Color(0xFF423733), surfaceDim = Color(0xFF1A110E), surfaceContainerLowest = Color(0xFF140C09), surfaceContainerLow = Color(0xFF231A16), surfaceContainer = Color(0xFF271E1A), surfaceContainerHigh = Color(0xFF322824), surfaceContainerHighest = Color(0xFF3D322F)
        )
    ),

    DINAMICO("Dinámico", null, null);

    companion object {
        /** Temas disponibles en este dispositivo (el dinámico requiere Android 12+). */
        val available: List<AppTheme>
            get() = entries.filter { it != DINAMICO || Build.VERSION.SDK_INT >= Build.VERSION_CODES.S }
    }

    @SuppressLint("NewApi")
    @Composable
    fun resolveScheme(darkTheme: Boolean = isSystemInDarkTheme()): ColorScheme {
        if (this == DINAMICO) {
            val context = LocalContext.current
            return if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        return (if (darkTheme) dark else light)!!
    }
}

@Composable
fun BlackjackTheme(theme: AppTheme, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = theme.resolveScheme(), content = content)
}