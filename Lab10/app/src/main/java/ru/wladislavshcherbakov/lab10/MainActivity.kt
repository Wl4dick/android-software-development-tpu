package ru.wladislavshcherbakov.lab10

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var edTextOfMessage: EditText
    private lateinit var edTextOfButton: EditText
    private lateinit var seekColorOfFonR: SeekBar
    private lateinit var seekColorOfFonG: SeekBar
    private lateinit var seekColorOfFonB: SeekBar
    private lateinit var seekColorOfTextR: SeekBar
    private lateinit var seekColorOfTextG: SeekBar
    private lateinit var seekColorOfTextB: SeekBar
    private lateinit var seekColorOfButtonR: SeekBar
    private lateinit var seekColorOfButtonG: SeekBar
    private lateinit var seekColorOfButtonB: SeekBar
    private lateinit var buttonShow: Button


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edTextOfMessage = findViewById(R.id.edTextMessage)
        edTextOfButton = findViewById(R.id.edTextOfButton)
        seekColorOfFonR = findViewById(R.id.seekColorOfFonR)
        seekColorOfFonG = findViewById(R.id.seekColorOfFonG)
        seekColorOfFonB = findViewById(R.id.seekColorOfFonB)
        seekColorOfTextR = findViewById(R.id.seekColorOfTextR)
        seekColorOfTextG = findViewById(R.id.seekColorOfTextG)
        seekColorOfTextB = findViewById(R.id.seekColorOfTextB)
        seekColorOfButtonR = findViewById(R.id.seekColorOfButtonR)
        seekColorOfButtonG = findViewById(R.id.seekColorOfButtonG)
        seekColorOfButtonB = findViewById(R.id.seekColorOfButtonB)
        buttonShow = findViewById(R.id.buttonShow)

        // Заданные значения по умолчанию
        edTextOfMessage.setText("Hello World!")
        edTextOfButton.setText("Nice day!")
        seekColorOfFonR.progress = 255
        seekColorOfFonG.progress = 255
        seekColorOfFonB.progress = 255
        seekColorOfTextR.progress = 255
        seekColorOfTextG.progress = 255
        seekColorOfTextB.progress = 255
        seekColorOfButtonR.progress = 255
        seekColorOfButtonG.progress = 255
        seekColorOfButtonB.progress = 255

        buttonShow.setOnClickListener{
            showSnackbar()
        }
    }

    @SuppressLint("ShowToast")
    private fun showSnackbar() {
        val message = edTextOfMessage.text.toString()
        val textOfButton = edTextOfButton.text.toString()
        val rootView: View = findViewById(android.R.id.content)
        val snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)

        val colorOfFon = Color.rgb(
            seekColorOfFonR.progress,
            seekColorOfFonG.progress,
            seekColorOfFonB.progress
        )

        snackbar.setBackgroundTint(colorOfFon)

        val textColor = Color.rgb(
            seekColorOfTextR.progress,
            seekColorOfTextG.progress,
            seekColorOfTextB.progress
        )

        val snackbarTextView = snackbar.setText(message).setTextColor(textColor)

        if (textOfButton.isNotEmpty()) {
            snackbar.setAction(textOfButton) {
                snackbar.dismiss()
            }

            val buttonTextColor = Color.rgb(
                seekColorOfButtonR.progress,
                seekColorOfButtonG.progress,
                seekColorOfButtonB.progress
            )
            snackbar.setActionTextColor(buttonTextColor)
        }

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edTextOfButton.windowToken, 0)
        imm.hideSoftInputFromWindow(edTextOfMessage.windowToken, 0)
        snackbar.setAnchorView(R.id.author)
        snackbar.show()
    }
}