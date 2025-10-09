package ru.wladislavshcherbakov.practice_1

import org.junit.Assert.assertEquals
import org.junit.Test

class MouseTest {

    @Test
    fun testGetMouseInfo() {
        val mouse = Mouse("Серый", 10)
        val expected = "Мышь по имени Серый бежит со скоростью 10"
        val actual = mouse.getMouseInfo()

        assertEquals(expected, actual)
    }

    @Test
    fun testDefaultName() {
        val mouse = Mouse(speed = 5) // имя не передаём, должно быть ""
        val expected = "Мышь по имени  бежит со скоростью 5"
        val actual = mouse.getMouseInfo()

        assertEquals(expected, actual)
    }
}