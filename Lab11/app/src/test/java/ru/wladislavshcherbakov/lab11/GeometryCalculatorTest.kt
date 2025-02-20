package ru.wladislavshcherbakov.lab11
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class GeometryCalculatorTest {

    private var calculator: GeometryCalculator? = null

    @Before
    fun init() {
        calculator = GeometryCalculator()
    }

    @Test
    fun testRectangleArea() {
        assertEquals(20.0, calculator?.rectangleArea(4.0, 5.0)!!, 0.0001)
        assertEquals(0.0, calculator?.rectangleArea(0.0, 5.0)!!, 0.0001)  // Граничное значение
    }

    @Test
    fun testRectanglePerimeter() {
        assertEquals(18.0, calculator?.rectanglePerimeter(4.0, 5.0)!!, 0.0001)
        assertEquals(10.0, calculator?.rectanglePerimeter(0.0, 5.0)!!, 0.0001)  // Граничное значение
    }

    @Test
    fun testCircleArea() {
        assertEquals(Math.PI * 4.0, calculator?.circleArea(2.0)!!, 0.0001)
        assertEquals(0.0, calculator?.circleArea(0.0)!!, 0.0001)  // Граничное значение
    }

    @After
    fun finalize() {
        calculator = null
    }

}