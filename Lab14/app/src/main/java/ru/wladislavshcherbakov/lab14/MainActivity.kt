package ru.wladislavshcherbakov.lab14

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(), CityListFragment.OnCitySelectedListener {

    private var isLandscape = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Теперь использует activity_main

        // Определяем, альбомная ли ориентация (проверяем, есть ли контейнер для деталей)
        isLandscape = findViewById<android.view.View>(R.id.detail_fragment_container) != null

        if (savedInstanceState == null) {
            // Первый запуск активности
            setupFragments()
        }
    }

    private fun setupFragments() {
        // Всегда добавляем фрагмент со списком
        val listFragment = CityListFragment()

        supportFragmentManager.commit {
            if (isLandscape) {
                // В альбомной ориентации используем list_fragment_container
                replace(R.id.list_fragment_container, listFragment)
            } else {
                // В портретной ориентации используем fragment_container
                replace(R.id.fragment_container, listFragment)
            }
            setReorderingAllowed(true)
        }

        // Если мы в альбомной ориентации, сразу добавляем и фрагмент с деталями (пустой)
        if (isLandscape) {
            val detailFragment = CityDetailFragment()
            supportFragmentManager.commit {
                replace(R.id.detail_fragment_container, detailFragment)
                setReorderingAllowed(true)
            }
        }
    }

    // Этот метод вызывается из CityListFragment, когда пользователь выбирает город
    override fun onCitySelected(city: City) {
        val detailFragment = CityDetailFragment.newInstance(city)

        supportFragmentManager.commit {
            if (isLandscape) {
                // В альбомной ориентации: заменяем детали в правом контейнере
                replace(R.id.detail_fragment_container, detailFragment)
            } else {
                // В портретной ориентации: заменяем список на детали в основном контейнере
                replace(R.id.fragment_container, detailFragment)
                // Добавляем в стек, чтобы кнопка "Назад" вернула к списку
                addToBackStack("city_detail")
            }
            setReorderingAllowed(true)
        }
    }
}
