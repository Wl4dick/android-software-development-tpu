package ru.wladislavshcherbakov.lab20

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            MainActivity.ACTION_SET_COLOR -> {
                val results = RemoteInput.getResultsFromIntent(intent)
                val color = results?.getCharSequence(KEY_COLOR_INPUT)?.toString()

                if (!color.isNullOrEmpty()) {
                    // Создаем интент для запуска MainActivity с цветом
                    val mainIntent = Intent(context, MainActivity::class.java).apply {
                        action = MainActivity.ACTION_SET_COLOR
                        putExtra(MainActivity.EXTRA_COLOR, color)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }
                    context.startActivity(mainIntent)
                }
            }
            MainActivity.ACTION_RESET_COLOR -> {
                // Создаем интент для запуска MainActivity с действием сброса
                val mainIntent = Intent(context, MainActivity::class.java).apply {
                    action = MainActivity.ACTION_RESET_COLOR
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                context.startActivity(mainIntent)
            }
        }
    }

    companion object {
        const val KEY_COLOR_INPUT = "key_color_input"
    }
}