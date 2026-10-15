package com.example.blackjack.data

import android.content.Context
import com.example.blackjack.TinyDB
import com.example.blackjack.logic.GameRecord

/**
 * Capa de persistencia sobre TinyDB.
 * Cada partida se guarda como un String; TinyDB guarda la lista completa.
 */
class ScoreStorage(context: Context) {

    private val db = TinyDB(context.applicationContext)

    // ---------- Historial ----------

    fun getRecords(): List<GameRecord> =
        rawRecords().mapNotNull(::decode)


    fun addRecord(record: GameRecord) {
        val current = ArrayList(rawRecords())
        current.add(0, encode(record))
        db.putListString(KEY_RECORDS, current)
    }

    fun clearRecords() = db.remove(KEY_RECORDS)

    // TinyDB devuelve [""] cuando la lista está vacía, por eso se filtra.
    private fun rawRecords(): List<String> =
        db.getListString(KEY_RECORDS).filter { it.isNotBlank() }

    // ---------- Ajustes ----------

    var themeIndex: Int
        get() = db.getInt(KEY_THEME)
        set(value) = db.putInt(KEY_THEME, value)

    var lastTarget: Int
        get() = db.getInt(KEY_TARGET).let { if (it == 0) 21 else it }
        set(value) = db.putInt(KEY_TARGET, value)

    var playerName: String
        get() = db.getString(KEY_NAME).ifBlank { "Jugador" }
        set(value) = db.putString(KEY_NAME, value)

    // ---------- Serialización ----------

    private fun clean(s: String) =
        s.replace(SEP, "/").replace(TINYDB_SEP, " ")

    private fun encode(r: GameRecord): String = listOf(
        clean(r.winner), clean(r.loser), r.timestamp, r.winningScore, r.target,
        r.winningCards.joinToString(" ")
    ).joinToString(SEP)

    private fun decode(raw: String): GameRecord? = runCatching {
        val p = raw.split(SEP, limit = 6)
        GameRecord(
            winner = p[0],
            loser = p[1],
            timestamp = p[2].toLong(),
            winningCards = p[5].split(" ").filter { it.isNotBlank() },
            winningScore = p[3].toInt(),
            target = p[4].toInt()
        )
    }.getOrNull()

    private companion object {
        const val KEY_RECORDS = "records"
        const val KEY_THEME = "theme_index"
        const val KEY_TARGET = "last_target"
        const val KEY_NAME = "player_name"
        const val SEP = "|"
        const val TINYDB_SEP = "\u201A\u2017\u201A"   // separador interno de TinyDB
    }
}