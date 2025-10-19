package ru.wladislavshcherbakov.practice_1.levels_service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.wladislavshcherbakov.practice_1.R
import ru.wladislavshcherbakov.practice_1.SharedViewModel

class LevelsFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_levels, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.levelsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val levels = listOf(
            Level(1, "Уровень 1", "Легкий"),
            Level(2, "Уровень 2", "Средний"),
            Level(3, "Уровень 3", "Сложный")
        )

        recyclerView.adapter = LevelsAdapter(levels) { level ->
            sharedViewModel.selectLevel(level)
        }

        return view
    }
}