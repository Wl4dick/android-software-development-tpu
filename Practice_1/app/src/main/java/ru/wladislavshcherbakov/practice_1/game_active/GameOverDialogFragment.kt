package ru.wladislavshcherbakov.practice_1.game_active

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.launch
import ru.wladislavshcherbakov.practice_1.AppDatabase
import ru.wladislavshcherbakov.practice_1.score_service.ScoreRecord
import java.time.LocalDate

class GameOverDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(score: Int) = GameOverDialogFragment().apply {
            arguments = bundleOf("score" to score)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val score = requireArguments().getInt("score")
        val input = EditText(requireContext()).apply { hint = "Имя игрока" }

        return AlertDialog.Builder(requireContext())
            .setTitle("Игра окончена")
            .setMessage("Ваш счёт: $score. Сохранить рекорд?")
            .setView(input)
            .setPositiveButton("Сохранить") { _, _ ->
                val name = input.text.toString().ifBlank { "Player" }
                lifecycleScope.launch {
                    val record = ScoreRecord(playerName = name, score = score, date = LocalDate.now().toString())
                    AppDatabase.getInstance(requireContext()).scoreRecordDao().insert(record)
                    Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Отмена", null)
            .create()
    }
}