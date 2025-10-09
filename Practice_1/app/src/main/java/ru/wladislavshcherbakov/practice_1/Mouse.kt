package ru.wladislavshcherbakov.practice_1

class Mouse(
    var name: String = "",   // по умолчанию пустая строка
    var speed: Int
) {
    fun getMouseInfo(): String {
        return "Мышь по имени $name бежит со скоростью $speed"
    }
}