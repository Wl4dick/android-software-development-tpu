package ru.wladislavshcherbakov.lab6

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val etA = findViewById<EditText>(R.id.editA)
        val etB = findViewById<EditText>(R.id.editB)
        val etC = findViewById<EditText>(R.id.editC)
        etA.doAfterTextChanged { calculateRoots(etA.text.toString().toDoubleOrNull(), etB.text.toString().toDoubleOrNull(), etC.text.toString().toDoubleOrNull()) }
        etB.doAfterTextChanged { calculateRoots(etA.text.toString().toDoubleOrNull(), etB.text.toString().toDoubleOrNull(), etC.text.toString().toDoubleOrNull()) }
        etC.doAfterTextChanged { calculateRoots(etA.text.toString().toDoubleOrNull(), etB.text.toString().toDoubleOrNull(), etC.text.toString().toDoubleOrNull()) }
    }

    private fun calculateRoots(a: Double?, b: Double?, c: Double?){
        val resultText = findViewById<TextView>(R.id.ResultText)
        val result1 = findViewById<TextView>(R.id.Result1)
        val result2 = findViewById<TextView>(R.id.Result2)
        if (a != null && b != null && c != null) {
            val (root1, root2) = solveQuadraticEquation(a, b, c)

            if( root1 != null && root2 != null ) {
                resultText.text = "Найдены два корня:"
                result1.text = "$root1"
                result2.text = "$root2"
            } else if ( root1 != null && root2 == null ) {
                resultText.text = "Найден один корень:"
                result1.text = "$root1"
                result2.text = ""
            } else {
                resultText.text = "Корней нет"
                result1.text = ""
                result2.text = ""
            }
        }
    }

    private fun solveQuadraticEquation(a: Double, b: Double, c: Double): Pair<Double?, Double?> {
        val discriminant = b * b - 4 * a * c

        return when {
            discriminant > 0.0 -> {
                val root1 = (-b + sqrt(discriminant)) / (2 * a)
                val root2 = (-b - sqrt(discriminant)) / (2 * a)
                Pair(root1, root2)
            }
            discriminant == 0.0 -> {
                val root = -b / (2 * a)
                Pair(root, null)
            }
            else -> {
                Pair(null, null) // Корней нет
            }
        }
    }
}