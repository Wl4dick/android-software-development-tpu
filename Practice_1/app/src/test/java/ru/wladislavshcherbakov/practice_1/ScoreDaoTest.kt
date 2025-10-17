package ru.wladislavshcherbakov.practice_1

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScoreRecordDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: ScoreRecordDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.scoreRecordDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insert_and_getAllSorted_returns_sorted_by_score_desc() = runBlocking {
        val record1 = ScoreRecord(id = 1, playerName = "Alice", score = 50, date = "2025-10-17")
        val record2 = ScoreRecord(id = 2, playerName = "Bob", score = 120, date = "2025-10-17")
        val record3 = ScoreRecord(id = 3, playerName = "Charlie", score = 90, date = "2025-10-17")

        dao.insert(record1)
        dao.insert(record2)
        dao.insert(record3)

        val result = dao.getAllSorted().first()

        assertEquals(3, result.size)
        assertEquals("Bob", result[0].playerName)      // 120
        assertEquals("Charlie", result[1].playerName)  // 90
        assertEquals("Alice", result[2].playerName)    // 50
    }
}
