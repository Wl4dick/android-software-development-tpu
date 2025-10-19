package ru.wladislavshcherbakov.practice_1.objects

data class Mouse(
    var x: Float,
    var y: Float,
    var radius: Float = 40f,
    var speed: Float = 0.35f
)