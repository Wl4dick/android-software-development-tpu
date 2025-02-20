package ru.wladislavshcherbakov.lab11

class GeometryCalculator {
    // Метод для вычисления площади прямоугольника
    fun rectangleArea(length: Double, width: Double): Double {
        return length * width
    }

    // Метод для вычисления периметра прямоугольника
    fun rectanglePerimeter(length: Double, width: Double): Double {
        return 2 * (length + width)
    }

    // Метод для вычисления площади круга
    fun circleArea(radius: Double): Double {
        return Math.PI * radius * radius
    }
}
