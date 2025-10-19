package ru.wladislavshcherbakov.practice_1.score_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.wladislavshcherbakov.practice_1.R

class ScoreService : Service() {

    private var score = 0
    private val channelId = "score_channel"

    override fun onCreate() {
        super.onCreate()
        createChannel()
        startForeground(1, buildNotification("Score: $score"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        score = intent?.getIntExtra("score", score) ?: score
        val nm = getSystemService(NotificationManager::class.java)
        nm.notify(1, buildNotification("Score: $score"))
        return START_STICKY
    }

    override fun onBind(intent: Intent?) = null

    private fun createChannel() {
        val ch = NotificationChannel(channelId, "Game score", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(ch)
    }

    private fun buildNotification(text: String): Notification {
        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_mouse)
            .setContentTitle("Кошки-мышки")
            .setContentText(text)
            .setOngoing(true)
            .build()
    }

    companion object {
        fun update(ctx: Context, score: Int) {
            val i = Intent(ctx, ScoreService::class.java).apply { putExtra("score", score) }
            ctx.startService(i)
        }
    }
}