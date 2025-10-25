package ru.wladislavshcherbakov.lab14

import android.os.Parcelable
import java.io.Serializable

data class City(
    val name: String,
    val region: String,
    val federalDistrict: String,
    val postalCode: String,
    val timeZone: String,
    val population: Int,
    val foundationYear: Int,
    val latitude: Double,
    val longitude: Double
) : Serializable
