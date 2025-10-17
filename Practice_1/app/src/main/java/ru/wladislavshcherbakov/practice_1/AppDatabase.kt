package ru.wladislavshcherbakov.practice_1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ScoreRecord::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreRecordDao(): ScoreRecordDao
}