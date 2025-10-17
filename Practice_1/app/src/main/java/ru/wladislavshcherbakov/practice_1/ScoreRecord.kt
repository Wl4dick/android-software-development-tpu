package ru.wladislavshcherbakov.practice_1

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_records")
data class ScoreRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val playerName: String,
    val score: Int,
    val date: String
)
