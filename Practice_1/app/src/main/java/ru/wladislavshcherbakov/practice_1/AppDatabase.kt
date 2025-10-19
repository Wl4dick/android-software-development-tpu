package ru.wladislavshcherbakov.practice_1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRecord
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRecordDao

@Database(entities = [ScoreRecord::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreRecordDao(): ScoreRecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "scores.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
