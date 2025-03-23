package ru.wladislavshcherbakov.lab13

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvCityInfo: TextView
    private lateinit var tvCityName: TextView
    private lateinit var tvFedOkrName: TextView
    private lateinit var tvRegionName: TextView
    private lateinit var tvMail: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvPopulation: TextView
    private lateinit var tvFounded: TextView
    private lateinit var btnSelectCity: Button
    private lateinit var btnShowOnMap: Button

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация View
        tvCityInfo = findViewById(R.id.tvCityInfo)
        tvCityName = findViewById(R.id.tvCityName)
        tvFedOkrName = findViewById(R.id.tvFedOkrName)
        tvRegionName = findViewById(R.id.tvRegionName)
        tvMail = findViewById(R.id.tvMail)
        tvTime = findViewById(R.id.tvTime)
        tvPopulation = findViewById(R.id.tvPopulation)
        tvFounded = findViewById(R.id.tvFounded)
        btnSelectCity = findViewById(R.id.btnSelectCity)
        btnShowOnMap = findViewById(R.id.btnShowOnMap)

        // Инициализация списка городов
        Common.initCities(this)

        // Обработка нажатия на кнопку выбора города
        btnSelectCity.setOnClickListener {
            val intent = Intent(this, CityListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_SELECT_CITY)
        }

        // Обработка нажатия на кнопку "Показать на карте"
        btnShowOnMap.setOnClickListener {
            val selectedCity = Common.cities.find { it.title == tvCityName.text.toString().removePrefix("Город: ") }
            selectedCity?.let {
                val geoUri = "geo:${it.lat},${it.lon}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                startActivity(intent)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SELECT_CITY && resultCode == RESULT_OK) {
            val selectedCityTitle = data?.getStringExtra("selectedCity")
            selectedCityTitle?.let {
                val city = Common.cities.find { city -> city.title == it }
                city?.let {
                    updateCityInfo(city)
                }
            }
        }
    }

    private fun updateCityInfo(city: City) {
        tvCityName.text = getString(R.string.city_prefix, city.title)
        tvFedOkrName.text = getString(R.string.fed_okrug_prefix, city.district)
        tvRegionName.text = getString(R.string.region_prefix, city.region)
        tvMail.text = getString(R.string.postal_code_prefix, city.postalCode)
        tvTime.text = getString(R.string.timezone_prefix, city.timezone)
        tvPopulation.text = getString(R.string.population_prefix, city.population)
        tvFounded.text = getString(R.string.founded_prefix, city.founded)
    }

    companion object {
        const val REQUEST_CODE_SELECT_CITY = 1
    }
}