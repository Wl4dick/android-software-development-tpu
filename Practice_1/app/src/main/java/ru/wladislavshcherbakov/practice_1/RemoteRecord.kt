package ru.wladislavshcherbakov.practice_1

data class RemoteRecord(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val date: String? = null
)
