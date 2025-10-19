package ru.wladislavshcherbakov.practice_1

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.wladislavshcherbakov.practice_1.levels_service.Level

class SharedViewModelTest {

    @Test
    fun testSelectLevel() {
        val viewModel = SharedViewModel()
        val level = Level(1, "Уровень 1", "Легкий")

        viewModel.selectLevel(level)

        assertEquals(level, viewModel.selectedLevel.value)
    }
}