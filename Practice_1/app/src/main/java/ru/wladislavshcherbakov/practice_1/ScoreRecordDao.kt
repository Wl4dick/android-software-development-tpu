package ru.wladislavshcherbakov.practice_1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreRecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: ScoreRecord)

    @Query("SELECT * FROM score_records ORDER BY score DESC")
    fun getAllSorted(): Flow<List<ScoreRecord>>
}