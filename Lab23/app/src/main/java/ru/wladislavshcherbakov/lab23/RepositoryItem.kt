package ru.wladislavshcherbakov.lab23

data class RepositoryItem(
    val name: String,
    val owner: Owner,
    val html_url: String,
    val description: String?,
    val language: String?
)
