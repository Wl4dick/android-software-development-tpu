package ru.wladislavshcherbakov.lab12

import ProductAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Создаем список товаров
        val products = listOf(
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food01)
                price.text = getString(R.string.food01_price)
                imageProduct.setImageResource(R.drawable.food01)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food02)
                price.text = getString(R.string.food02_price)
                imageProduct.setImageResource(R.drawable.food02)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food03)
                price.text = getString(R.string.food03_price)
                imageProduct.setImageResource(R.drawable.food03)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food04)
                price.text = getString(R.string.food04_price)
                imageProduct.setImageResource(R.drawable.food04)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food05)
                price.text = getString(R.string.food05_price)
                imageProduct.setImageResource(R.drawable.food05)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food06)
                price.text = getString(R.string.food06_price)
                imageProduct.setImageResource(R.drawable.food06)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food07)
                price.text = getString(R.string.food07_price)
                imageProduct.setImageResource(R.drawable.food07)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food08)
                price.text = getString(R.string.food08_price)
                imageProduct.setImageResource(R.drawable.food08)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food09)
                price.text = getString(R.string.food09_price)
                imageProduct.setImageResource(R.drawable.food09)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food10)
                price.text = getString(R.string.food10_price)
                imageProduct.setImageResource(R.drawable.food10)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food11)
                price.text = getString(R.string.food11_price)
                imageProduct.setImageResource(R.drawable.food11)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food12)
                price.text = getString(R.string.food12_price)
                imageProduct.setImageResource(R.drawable.food12)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food13)
                price.text = getString(R.string.food13_price)
                imageProduct.setImageResource(R.drawable.food13)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food14)
                price.text = getString(R.string.food14_price)
                imageProduct.setImageResource(R.drawable.food14)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food15)
                price.text = getString(R.string.food15_price)
                imageProduct.setImageResource(R.drawable.food15)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food16)
                price.text = getString(R.string.food16_price)
                imageProduct.setImageResource(R.drawable.food16)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food17)
                price.text = getString(R.string.food17_price)
                imageProduct.setImageResource(R.drawable.food17)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food18)
                price.text = getString(R.string.food18_price)
                imageProduct.setImageResource(R.drawable.food18)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food19)
                price.text = getString(R.string.food19_price)
                imageProduct.setImageResource(R.drawable.food19)
            },
            Product(LayoutInflater.from(this).inflate(R.layout.item_product, null)).apply {
                name.text = getString(R.string.food20)
                price.text = getString(R.string.food20_price)
                imageProduct.setImageResource(R.drawable.food20)
            }
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(products, this)
    }
}