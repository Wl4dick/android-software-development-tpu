package ru.wladislavshcherbakov.practice_1

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.formatDate(): String {
    return try {
        // Парсим строку в формате ISO (yyyy-MM-dd)
        val input = LocalDate.parse(this, DateTimeFormatter.ISO_DATE)
        // Форматируем в dd.MM.yyyy
        input.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    } catch (e: Exception) {
        // Если строка не в нужном формате — возвращаем её как есть
        this
    }
}
