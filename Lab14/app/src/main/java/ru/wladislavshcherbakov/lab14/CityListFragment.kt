package ru.wladislavshcherbakov.lab14

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class CityListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    private var scrollState: Parcelable? = null

    interface OnCitySelectedListener {
        fun onCitySelected(city: City)
    }

    private var citySelectedListener: OnCitySelectedListener? = null
    private var cities: List<City> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("CityListFragment: onViewCreated called")
        if (savedInstanceState != null) {
            scrollState = savedInstanceState.getParcelable("scroll_state", Parcelable::class.java)
        }

        setupRecyclerView(view)
        citySelectedListener = parentFragment as? OnCitySelectedListener ?: activity as? OnCitySelectedListener
    }

    private fun setupRecyclerView(view: View) {
        println("setupRecyclerView: started")

        recyclerView = view.findViewById(R.id.recyclerView)
        println("setupRecyclerView: recyclerView found = ${recyclerView != null}")

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Загружаем города из CSV файла напрямую
        cities = loadCitiesFromCSV()
        println("setupRecyclerView: cities loaded = ${cities.size}")

        adapter = CityAdapter(cities) { city ->
            println("setupRecyclerView: city clicked = ${city.name}")
            citySelectedListener?.onCitySelected(city)
        }
        recyclerView.adapter = adapter
        println("setupRecyclerView: adapter set")

        scrollState?.let {
            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }

        println("setupRecyclerView: completed")
    }

    private fun loadCitiesFromCSV(): List<City> {
        val cities = mutableListOf<City>()

        try {
            println("loadCitiesFromCSV: starting CSV load")

            val inputStream = resources.openRawResource(R.raw.cities)
            val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))

            // Пропускаем заголовок если он есть
            val firstLine = reader.readLine()
            println("loadCitiesFromCSV: first line = $firstLine")

            var lineCount = 0
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                lineCount++
                line?.let { csvLine ->
                    try {
                        // Разделяем по точке с запятой
                        val values = csvLine.split(";")

                        if (values.size >= 9) {
                            // Судя по примеру строки, порядок полей в твоем CSV:
                            // [0] - почтовый индекс
                            // [1] - федеральный округ
                            // [2] - регион
                            // [3] - название города
                            // [4] - часовой пояс
                            // [5] - широта
                            // [6] - долгота
                            // [7] - население
                            // [8] - год основания

                            val city = City(
                                name = values[3].trim(),
                                region = values[2].trim(),
                                federalDistrict = values[1].trim(),
                                postalCode = values[0].trim(),
                                timeZone = values[4].trim(),
                                population = values[7].trim().toIntOrNull() ?: 0,
                                foundationYear = values[8].trim().toIntOrNull() ?: 0,
                                latitude = values[5].trim().toDoubleOrNull() ?: 0.0,
                                longitude = values[6].trim().toDoubleOrNull() ?: 0.0
                            )
                            cities.add(city)
                            if (lineCount <= 5) { // Выведем первые 5 городов для проверки
                                println("loadCitiesFromCSV: added city - ${city.name}")
                            }
                        } else {
                            println("loadCitiesFromCSV: skipping line $lineCount - not enough values: $csvLine")
                        }
                    } catch (e: Exception) {
                        println("loadCitiesFromCSV: error parsing line $lineCount: $csvLine, error: ${e.message}")
                    }
                }
            }

            reader.close()
            inputStream.close()
            println("loadCitiesFromCSV: successfully loaded ${cities.size} cities from CSV")

        } catch (e: Exception) {
            println("loadCitiesFromCSV: Exception: ${e.message}")
            e.printStackTrace()

            // Fallback на тестовые данные при ошибке
            cities.addAll(listOf(
                City("Москва", "Московская область", "Центральный", "101000", "UTC+3", 13000000, 1147, 55.7558, 37.6173),
                City("Санкт-Петербург", "Ленинградская область", "Северо-Западный", "190000", "UTC+3", 5400000, 1703, 59.9343, 30.3351),
                City("Томск", "Томская область", "Сибирский", "634000", "UTC+7", 522940, 1604, 56.4977, 84.9744)
            ))
            println("loadCitiesFromCSV: using ${cities.size} fallback cities")
        }

        return cities
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        scrollState = recyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable("scroll_state", scrollState)
    }

    override fun onPause() {
        super.onPause()
        scrollState = recyclerView.layoutManager?.onSaveInstanceState()
    }
}