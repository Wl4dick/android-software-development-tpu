package ru.wladislavshcherbakov.practice_1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var musicSwitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var sfxSwitch: Switch
    private lateinit var playerNameEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        musicSwitch = view.findViewById(R.id.musicSwitch)
        sfxSwitch = view.findViewById(R.id.sfxSwitch)
        playerNameEditText = view.findViewById(R.id.playerNameEditText)

        val prefs = requireActivity().getSharedPreferences("game_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        // Загружаем сохранённые значения
        musicSwitch.isChecked = prefs.getBoolean("music_enabled", true)
        sfxSwitch.isChecked = prefs.getBoolean("sfx_enabled", true)
        playerNameEditText.setText(prefs.getString("player_name", "Игрок"))

        // Сохраняем при изменении
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("music_enabled", isChecked).apply()
        }

        sfxSwitch.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("sfx_enabled", isChecked).apply()
        }

        playerNameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                editor.putString("player_name", playerNameEditText.text.toString()).apply()
            }
        }

        return view
    }
}
