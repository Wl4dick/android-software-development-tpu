package ru.wladislavshcherbakov.lab9

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCountry: Spinner
    private lateinit var textViewPhoneCode: TextView
    private lateinit var checkBoxAgreement: CheckBox
    private lateinit var buttonRegister: Button
    private lateinit var buttonCancel: Button

    private val countryCodes = mapOf(
        "Россия" to "+7",
        "Уругвай" to "+598",
        "Беларусь" to "+375",
        "Монголия" to "+976",
        "Армения" to "+374",
        "Кыргызстан" to "+996"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerCountry = findViewById(R.id.countries)
        textViewPhoneCode = findViewById(R.id.code_number)
        checkBoxAgreement = findViewById(R.id.agreement)
        buttonRegister = findViewById(R.id.regButton)
        buttonRegister.isEnabled = false
        buttonCancel = findViewById(R.id.rejectionButton)

        setupSpinner()
        setupListeners()
    }

    private fun setupSpinner() {
        val countries = countryCodes.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = adapter

        spinnerCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCountry = countries[position]
                textViewPhoneCode.text = countryCodes[selectedCountry]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupListeners() {
        checkBoxAgreement.setOnCheckedChangeListener { _, isChecked ->
            buttonRegister.isEnabled = isChecked
            if (isChecked) {
            buttonRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            } else {
                buttonRegister.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
            }
        }

        buttonRegister.setOnClickListener{
            Toast.makeText(this, "Регистрация успешна!", Toast.LENGTH_SHORT).show()
        }

        buttonCancel.setOnClickListener{
            finish()
        }
    }
}