package ru.wladislavshcherbakov.lab17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.wladislavshcherbakov.lab17.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var newsCount = 0
    private var isInNewsSection = false
    private var timer: android.os.CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()
        showFragment(MusicFragment())
        startNewsTimer()
    }

    private fun setupBottomNavigation() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_music -> {
                    showFragment(MusicFragment())
                    isInNewsSection = false
                    true
                }
                R.id.menu_books -> {
                    showFragment(BooksFragment())
                    isInNewsSection = false
                    true
                }
                R.id.menu_news -> {
                    showFragment(NewsFragment())
                    isInNewsSection = true
                    updateNewsBadge(0) // Сбрасываем бейдж при входе в новости
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun startNewsTimer() {
        timer = object : android.os.CountDownTimer(Long.MAX_VALUE, 3000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isInNewsSection) {
                    newsCount++
                    updateNewsBadge(newsCount)
                }
            }

            override fun onFinish() {
                // Таймер будет работать бесконечно
            }
        }
        timer?.start()
    }

    fun updateNewsBadge(count: Int) {
        val badge = binding.bottomNav.getOrCreateBadge(R.id.menu_news)
        if (count > 0) {
            badge.number = count
            badge.isVisible = true
        } else {
            badge.isVisible = false
            newsCount = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}