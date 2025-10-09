package ru.wladislavshcherbakov.practice_1

import org.junit.Assert.assertEquals
import org.junit.Test

class SharedViewModelTest {

    @Test
    fun testSelectLevel() {
        val viewModel = SharedViewModel()
        val level = Level(1, "Уровень 1", "Легкий")

        viewModel.selectLevel(level)

        assertEquals(level, viewModel.selectedLevel.value)
    }
}