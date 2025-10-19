package ru.wladislavshcherbakov.practice_1

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import ru.wladislavshcherbakov.practice_1.game_active.GameFragment
import ru.wladislavshcherbakov.practice_1.levels_service.LevelsFragment
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRecord
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: LeaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton: Button = findViewById(R.id.startButton)
        val levelsButton: Button = findViewById(R.id.levelsButton)
        val settingsButton: Button = findViewById(R.id.settingsButton)

        findViewById<Button>(R.id.startButton).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GameFragment())
                .addToBackStack(null)
                .commit()
        }

        // Логика из первого задания
        startButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, GameFragment())
                .addToBackStack(null)
                .commit()
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
                            date = LocalDate.now().toString().formatDate()
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