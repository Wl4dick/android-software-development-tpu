package ru.wladislavshcherbakov.lab8

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ConversionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[ConversionViewModel::class.java]

        val etMeters: EditText = findViewById(R.id.etMeters)
        val etInches: EditText = findViewById(R.id.etInches)
        val etYards: EditText = findViewById(R.id.etYards)
        val etFeet: EditText = findViewById(R.id.etFeet)

        viewModel.meters.observe(this) { value ->
            if (etMeters.text.toString() != value) {
                etMeters.setText(value)
            }
        }


        viewModel.inches.observe(this) { value ->
            if (etInches.text.toString() != value) {
                etInches.setText(value)
            }
        }

        viewModel.yards.observe(this) { value ->
            if (etYards.text.toString() != value) {
                etYards.setText(value)
            }
        }

        viewModel.feet.observe(this) { value ->
            if (etFeet.text.toString() != value) {
                etFeet.setText(value)
            }
        }

        etMeters.doAfterTextChanged {
            viewModel.updateMeters(it.toString())
        }

        etInches.doAfterTextChanged {
            viewModel.updateInches(it.toString())
        }

        etYards.doAfterTextChanged {
            viewModel.updateYards(it.toString())
        }

        etFeet.doAfterTextChanged {
            viewModel.updateFeet(it.toString())
        }
    }

}