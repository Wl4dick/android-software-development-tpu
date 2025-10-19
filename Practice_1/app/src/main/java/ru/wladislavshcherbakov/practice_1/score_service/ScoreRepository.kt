package ru.wladislavshcherbakov.practice_1.score_service

import kotlinx.coroutines.flow.Flow
import ru.wladislavshcherbakov.practice_1.RemoteApi
import java.time.LocalDate

class ScoreRepository(
    private val dao: ScoreRecordDao,
    private val api: RemoteApi
) {
    // Получение локальных рекордов из Room
    fun getLocalScores(): Flow<List<ScoreRecord>> = dao.getAllSorted()

    // Сохранение нового рекорда
    suspend fun save(record: ScoreRecord) {
        dao.insert(record)
    }

    // Загрузка топ-5 с сервера и преобразование в ScoreRecord
    suspend fun fetchRemoteTop5(): List<ScoreRecord> {
        val remote = api.getPosts().take(5)
        return remote.map {
            ScoreRecord(
                playerName = it.title,
                score = it.id,
                date = LocalDate.now().toString()
            )
        }
    }
}
