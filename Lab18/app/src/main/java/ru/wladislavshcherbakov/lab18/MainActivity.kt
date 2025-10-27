package ru.wladislavshcherbakov.lab18

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var getRatesButton: Button
    private lateinit var currencyListView: ListView
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingText: TextView

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupButtonClickListener()
    }

    private fun initializeViews() {
        getRatesButton = findViewById(R.id.getRatesButton)
        currencyListView = findViewById(R.id.currencyListView)
        progressBar = findViewById(R.id.progressBar)
        loadingText = findViewById(R.id.loadingText)
    }

    private fun setupButtonClickListener() {
        getRatesButton.setOnClickListener {
            loadCurrencyRates()
        }
    }

    private fun loadCurrencyRates() {
        showLoading(true)

        scope.launch {
            try {
                val currencies = withContext(Dispatchers.IO) {
                    downloadAndParseCurrencies()
                }
                if (currencies.isNotEmpty()) {
                    showCurrencies(currencies)
                    Toast.makeText(this@MainActivity, "Курсы загружены", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Не удалось загрузить данные", Toast.LENGTH_LONG).show()
                    showLoading(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
                showLoading(false)
            }
        }
    }

    private fun downloadAndParseCurrencies(): List<Currency> {
        var connection: HttpURLConnection? = null
        try {
            val url = URL("https://www.cbr.ru/scripts/XML_daily.asp")
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream, Charset.forName("Windows-1251")))
                val xml = reader.use { it.readText() }
                reader.close()

                return parseCurrenciesFromXml(xml)
            } else {
                println("HTTP error: $responseCode")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        } finally {
            connection?.disconnect()
        }
        return emptyList()
    }

    private fun parseCurrenciesFromXml(xml: String): List<Currency> {
        val currencies = mutableListOf<Currency>()

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(StringReader(xml))

            var eventType = parser.eventType
            var currentTag = ""
            var name = ""
            var value = ""
            var charCode = ""

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        currentTag = parser.name
                        if (parser.name == "Valute") {
                            // Сбрасываем значения для новой валюты
                            name = ""
                            value = ""
                            charCode = ""
                        }
                    }

                    XmlPullParser.TEXT -> {
                        when (currentTag) {
                            "Name" -> name = parser.text
                            "Value" -> value = parser.text
                            "CharCode" -> charCode = parser.text
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == "Valute") {
                            if (name.isNotEmpty() && value.isNotEmpty()) {
                                currencies.add(Currency(name, value, charCode))
                            }
                        }
                        currentTag = ""
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return currencies
    }

    private fun showCurrencies(currencies: List<Currency>) {
        val adapter = CurrencyAdapter(currencies)
        currencyListView.adapter = adapter

        showLoading(false)
        currencyListView.visibility = View.VISIBLE
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        loadingText.visibility = if (show) View.VISIBLE else View.GONE
        getRatesButton.isEnabled = !show

        if (show) {
            currencyListView.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}