package ru.wladislavshcherbakov.lab14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class CityDetailFragment : Fragment() {

    private lateinit var title: TextView
    private lateinit var federalDistrict: TextView
    private lateinit var region: TextView
    private lateinit var postalCode: TextView
    private lateinit var timeZone: TextView
    private lateinit var population: TextView
    private lateinit var foundationYear: TextView
    private lateinit var showOnMapButton: Button

    private var currentCity: City? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupClickListeners()

        arguments?.getSerializable("selected_city")?.let { city ->
            showCity(city as City)
        }
    }

    private fun initViews(view: View) {
        title = view.findViewById(R.id.title)
        federalDistrict = view.findViewById(R.id.federalDistrict)
        region = view.findViewById(R.id.region)
        postalCode = view.findViewById(R.id.postalCode)
        timeZone = view.findViewById(R.id.timeZone)
        population = view.findViewById(R.id.population)
        foundationYear = view.findViewById(R.id.foundationYear)
        showOnMapButton = view.findViewById(R.id.showOnMapButton)
    }

    private fun setupClickListeners() {
        showOnMapButton.setOnClickListener {
            currentCity?.let { city ->
                // Просто скрываем кнопку после нажатия или можно оставить функциональность
                showOnMapButton.text = "Карта недоступна"
                showOnMapButton.isEnabled = false
            }
        }
    }

    fun showCity(city: City) {
        currentCity = city

        // Убедись, что здесь нет %s - используем прямое присвоение текста
        title.text = "Город: ${city.name}"
        federalDistrict.text = "Федеральный округ: ${city.federalDistrict}"
        region.text = "Регион: ${city.region}"
        postalCode.text = "Почтовый индекс: ${city.postalCode}"
        timeZone.text = "Часовой пояс: ${city.timeZone}"
        population.text = "Население: ${city.population}"
        foundationYear.text = "Основан в: ${city.foundationYear} году"
    }

    companion object {
        fun newInstance(city: City): CityDetailFragment {
            val fragment = CityDetailFragment()
            val args = Bundle()
            args.putSerializable("selected_city", city)
            fragment.arguments = args
            return fragment
        }
    }
}