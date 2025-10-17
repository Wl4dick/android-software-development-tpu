package ru.wladislavshcherbakov.practice_1

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: RemoteApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // базовый адрес
            .addConverterFactory(GsonConverterFactory.create()) // конвертер JSON
            .build()
            .create(RemoteApi::class.java) // создаём реализацию интерфейса
    }
}