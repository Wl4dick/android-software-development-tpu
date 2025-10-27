@file:Suppress("DEPRECATED_ANNOTATION")

package ru.wladislavshcherbakov.lab15

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingItem(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val quantity: String
) : Parcelable
