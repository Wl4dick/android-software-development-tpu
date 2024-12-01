package ru.wladislavshcherbakov.lab7

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var edibleButton: Button
    private lateinit var notEdibleButton: Button
    private lateinit var correctCountTextView: TextView
    private lateinit var incorrectCountTextView: TextView
    private var correctCount = 0
    private var incorrectCount = 0
    private var currentItem : Item? = null

    data class Item(val imageName: String, val isEdible: Boolean)

    private val items = listOf(
        Item("food01", true),
        Item("food02", true),
        Item("food03", true),
        Item("food04", true),
        Item("food05", true),
        Item("food06", true),
        Item("food07", true),
        Item("food08", true),
        Item("food09", true),
        Item("food10", true),
        Item("food11", true),
        Item("food12", true),
        Item("food13", true),
        Item("food14", true),
        Item("food15", true),
        Item("food16", true),
        Item("food17", true),
        Item("food18", true),
        Item("food19", true),
        Item("food20", true),
        Item("sport01", false),
        Item("sport02", false),
        Item("sport03", false),
        Item("sport04", false),
        Item("sport05", false),
        Item("sport06", false),
        Item("sport07", false),
        Item("sport08", false),
        Item("sport09", false),
        Item("sport10", false),
        Item("sport11", false),
        Item("sport12", false),
        Item("sport13", false),
        Item("sport14", false),
        Item("sport15", false),
        Item("sport16", false),
        Item("sport17", false),
        Item("sport18", false),
        Item("sport19", false),
        Item("sport20", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        edibleButton = findViewById(R.id.edibleButton)
        notEdibleButton = findViewById(R.id.notEdibleButton)
        correctCountTextView = findViewById(R.id.correctCountTextView)
        incorrectCountTextView = findViewById(R.id.incorrectCountTextView)

        updateImage()

        edibleButton.setOnClickListener { checkAnswer(true) }
        notEdibleButton.setOnClickListener { checkAnswer(false) }
    }

    @SuppressLint("DiscouragedApi")
    private fun updateImage() {
        val randomIndex = Random.nextInt(items.size)
        currentItem = items[randomIndex]
        val imageName = currentItem?.imageName ?: "food01"
        val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
        imageView.setImageResource(resourceId)
    }

    private fun checkAnswer(isEdible: Boolean) {
        if (isEdible == currentItem?.isEdible) {
            correctCount++
        } else {
            incorrectCount++
        }
        updateScores()
        updateImage()
    }

    @SuppressLint("SetTextI18n")
    private fun updateScores() {
        correctCountTextView.text = "Правильно: $correctCount"
        incorrectCountTextView.text = "Неправильно: $incorrectCount"
    }
}