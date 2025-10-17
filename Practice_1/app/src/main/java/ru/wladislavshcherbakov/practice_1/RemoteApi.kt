package ru.wladislavshcherbakov.practice_1

import retrofit2.http.GET

interface RemoteApi {
    @GET("posts")
    suspend fun getPosts(): List<RemoteRecord>
}