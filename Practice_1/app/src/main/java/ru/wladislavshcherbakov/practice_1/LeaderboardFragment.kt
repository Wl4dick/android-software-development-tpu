package ru.wladislavshcherbakov.practice_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class LeaderboardFragment : Fragment(R.layout.fragment_leaderboard) {

    private lateinit var adapter: LeaderboardAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LeaderboardAdapter()
        val recycler = view.findViewById<RecyclerView>(R.id.leaderboardRecyclerView)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        val button = view.findViewById<Button>(R.id.loadRemoteButton)
        button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    // Загружаем топ-5 с сервера
                    val remoteRecords = RetrofitInstance.api.getPosts().take(5)

                    // Преобразуем RemoteRecord → ScoreRecord для отображения
                    val mapped = remoteRecords.map {
                        ScoreRecord(
                            playerName = it.title, // временно используем title как имя
                            score = it.id,         // временно используем id как очки
                            date = it.date?.formatDate() ?: "—"
                        )
                    }

                    // Передаём список в адаптер
                    adapter.submitList(mapped)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(),
                        "Ошибка загрузки: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
