package ru.wladislavshcherbakov.practice_1.game_active

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.wladislavshcherbakov.practice_1.R

class GameFragment : Fragment() {

    private lateinit var gameView: GameView
    private var mediaPlayer: MediaPlayer? = null
    private var soundPool: SoundPool? = null
    private var cheeseSoundId = 0

    private lateinit var prefs: SharedPreferences
    private var sfxEnabled = true

    private lateinit var scoreText: TextView

    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        when (key) {
            "music_enabled" -> {
                val enabled = prefs.getBoolean("music_enabled", true)
                if (enabled) {
                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bg_music).apply {
                            isLooping = true
                            start()
                        }
                    } else {
                        mediaPlayer?.start()
                    }
                } else {
                    mediaPlayer?.pause()
                }
            }
            "sfx_enabled" -> {
                sfxEnabled = prefs.getBoolean("sfx_enabled", true)
                if (sfxEnabled) {
                    gameView.onCheeseEaten = { soundPool?.play(cheeseSoundId, 1f, 1f, 1, 0, 1f) }
                } else {
                    gameView.onCheeseEaten = { /* ничего не делаем */ }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val frame = FrameLayout(requireContext())

        gameView = GameView(requireContext()) { score ->
            showGameOverDialog(score)
        }

        scoreText = TextView(requireContext()).apply {
            textSize = 24f
            setTextColor(Color.WHITE)
            setPadding(16, 16, 16, 16)
            text = "0"
        }

        // обновляем TextView при изменении счёта
        gameView.onScoreChanged = { score ->
            scoreText.text = score.toString()
        }

        frame.addView(gameView)
        frame.addView(scoreText)

        return frame
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefs = requireActivity().getSharedPreferences("game_prefs", Context.MODE_PRIVATE)

        val musicEnabled = prefs.getBoolean("music_enabled", true)
        sfxEnabled = prefs.getBoolean("sfx_enabled", true)

        if (musicEnabled) {
            mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bg_music).apply {
                isLooping = true
                start()
            }
        }

        soundPool = SoundPool.Builder().setMaxStreams(3).build()
        cheeseSoundId = soundPool!!.load(requireContext(), R.raw.cheese_eat, 1)

        if (sfxEnabled) {
            gameView.onCheeseEaten = { soundPool?.play(cheeseSoundId, 1f, 1f, 1, 0, 1f) }
        } else {
            gameView.onCheeseEaten = { /* пусто */ }
        }
    }

    private fun showGameOverDialog(score: Int) {
        mediaPlayer?.pause()
        GameOverDialogFragment.newInstance(score).show(parentFragmentManager, "game_over")
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
        prefs.registerOnSharedPreferenceChangeListener(listener)

        if (prefs.getBoolean("music_enabled", true)) {
            mediaPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
        mediaPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release(); mediaPlayer = null
        soundPool?.release(); soundPool = null
    }
}
