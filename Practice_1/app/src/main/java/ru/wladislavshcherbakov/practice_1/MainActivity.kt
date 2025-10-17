package ru.wladislavshcherbakov.practice_1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        val levelsButton: Button = findViewById(R.id.levelsButton)
        val settingsButton: Button = findViewById(R.id.settingsButton)

        // Логика из первого задания
        startButton.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_SHORT).show()
        }

        // Переход к списку уровней
        levelsButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LevelsFragment())
                .addToBackStack(null)
                .commit()
        }

        // Переход к экрану настроек
        settingsButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SettingsFragment())
                .addToBackStack(null)
                .commit()
        }

        adapter = LeaderboardAdapter()
        val recycler = findViewById<RecyclerView>(R.id.leaderboardRecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val loadButton = findViewById<Button>(R.id.loadRemoteButton)
        loadButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val remoteRecords = RetrofitInstance.api.getPosts().take(5)

                    val mapped = remoteRecords.map {
                        ScoreRecord(
                            playerName = it.title,
                            score = it.id,
                            date = java.time.LocalDate.now().toString().formatDate()
                        )
                    }

                    adapter.submitList(mapped)

                } catch (e: Exception) {
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка загрузки: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}