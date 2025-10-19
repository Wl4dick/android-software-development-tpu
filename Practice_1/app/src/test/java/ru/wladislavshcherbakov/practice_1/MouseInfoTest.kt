package ru.wladislavshcherbakov.practice_1

import org.junit.Assert.assertEquals
import org.junit.Test

class MouseInfoTest {

    @Test
    fun testGetMouseInfo() {
        val mouse = MouseInfo("Серый", 10)
        val expected = "Мышь по имени Серый бежит со скоростью 10"
        val actual = mouse.getMouseInfo()

        assertEquals(expected, actual)
    }

    @Test
    fun testDefaultName() {
        val mouse = MouseInfo(speed = 5) // имя не передаём, должно быть ""
        val expected = "Мышь по имени  бежит со скоростью 5"
        val actual = mouse.getMouseInfo()

        assertEquals(expected, actual)
    }
}