package ru.wladislavshcherbakov.practice_1

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRecord
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRecordDao
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRepository

class ScoreRepositoryTest {

    private lateinit var dao: ScoreRecordDao
    private lateinit var api: RemoteApi
    private lateinit var repository: ScoreRepository

    @Before
    fun setup() {
        dao = mock(ScoreRecordDao::class.java)
        api = mock(RemoteApi::class.java)
        repository = ScoreRepository(dao, api)
    }

    @Test
    fun getLocalScores_returnsFlowFromDao() = runBlocking {
        val fakeList = listOf(
            ScoreRecord(1, "Alice", 120, "2025-10-17"),
            ScoreRecord(2, "Bob", 90, "2025-10-17")
        )
        `when`(dao.getAllSorted()).thenReturn(flowOf(fakeList))

        val result = repository.getLocalScores().first()

        assertEquals(2, result.size)
        assertEquals("Alice", result[0].playerName)
    }

    @Test
    fun fetchRemoteTop5_mapsRemoteRecords() = runBlocking {
        val remote = listOf(
            RemoteRecord(1, 1, "Title1", "Body1"),
            RemoteRecord(1, 2, "Title2", "Body2")
        )
        `when`(api.getPosts()).thenReturn(remote)

        val result = repository.fetchRemoteTop5()

        assertEquals(2, result.size)
        assertEquals("Title1", result[0].playerName)
        assertEquals(1, result[0].score)
    }

    @Test
    fun save_callsDaoInsert() = runBlocking {
        val record = ScoreRecord(3, "Charlie", 77, "2025-10-17")

        repository.save(record)

        verify(dao, times(1)).insert(record)
    }
}
