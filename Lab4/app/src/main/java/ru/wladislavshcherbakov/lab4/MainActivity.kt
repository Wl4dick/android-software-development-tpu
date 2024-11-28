package ru.wladislavshcherbakov.lab4

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Тут будет код программы
        val etFirst = findViewById<EditText>(R.id.etFirst)
        val etSecond = findViewById<EditText>(R.id.etSecond)
        val btPlus = findViewById<TextView>(R.id.btPlus)
        val btMinus = findViewById<TextView>(R.id.btMinus)
        val btMulti = findViewById<TextView>(R.id.btMulti)
        val btDiv = findViewById<TextView>(R.id.btDiv)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        btPlus.setOnClickListener {
            val num1 = etFirst.text.toString().toFloatOrNull()
            val num2 = etSecond.text.toString().toFloatOrNull()
            if (num1 != null && num2 != null)
                tvResult.text = "$num1 + $num2 = ${num1 + num2}"
            else
                tvResult.text = "Неверное число!"
        }
        btMinus.setOnClickListener {
            val num1 = etFirst.text.toString().toFloatOrNull()
            val num2 = etSecond.text.toString().toFloatOrNull()
            if (num1 != null && num2 != null)
                tvResult.text = "$num1 - $num2 = ${num1 - num2}"
            else
                tvResult.text = "Неверное число!"
        }
        btMulti.setOnClickListener {
            val num1 = etFirst.text.toString().toFloatOrNull()
            val num2 = etSecond.text.toString().toFloatOrNull()
            if (num1 != null && num2 != null)
                tvResult.text = "$num1 * $num2 = ${num1 * num2}"
            else
                tvResult.text = "Неверное число!"
        }
        btDiv.setOnClickListener {
            val num1 = etFirst.text.toString().toFloatOrNull()
            val num2 = etSecond.text.toString().toFloatOrNull()
            if (num1 != null && num2 != null)
                tvResult.text = "$num1 / $num2 = ${num1 / num2}"
            else
                tvResult.text = "Неверное число!"
        }
    }
}