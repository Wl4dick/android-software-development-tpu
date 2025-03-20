package ru.wladislavshcherbakov.lab12

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Product(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView
    var price: TextView
    var imageProduct: ImageView
    var addImage: ImageView
    var isAddedToCart: Boolean = false

    init {
        name = itemView.findViewById(R.id.title)
        price = itemView.findViewById(R.id.subhead)
        imageProduct = itemView.findViewById(R.id.header_image)
        addImage = itemView.findViewById(R.id.buttonAddToCart)
    }
}