package ru.wladislavshcherbakov.lab20

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import android.widget.Toast

class NotificationManager(private val context: Context) {

    private val notificationManager = NotificationManagerCompat.from(context)
    private val channelId = context.getString(R.string.notification_channel_id)

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                context.getString(R.string.notification_channel_name),
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.notification_channel_description)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    fun showNotification() {
        // Проверяем разрешение на показ уведомлений
        if (!notificationManager.areNotificationsEnabled()) {
            Toast.makeText(context, "Уведомления отключены для этого приложения", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Интент для открытия приложения при нажатии на уведомление
            val contentIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            val contentPendingIntent = PendingIntent.getActivity(
                context,
                0,
                contentIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Действие сброса цвета
            val resetIntent = Intent(context, NotificationReceiver::class.java).apply {
                action = MainActivity.ACTION_RESET_COLOR
            }
            val resetPendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                resetIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Действие задания цвета с полем ввода
            val remoteInput = RemoteInput.Builder(NotificationReceiver.KEY_COLOR_INPUT).run {
                setLabel(context.getString(R.string.color_input_label))
                build()
            }

            val setColorIntent = Intent(context, NotificationReceiver::class.java).apply {
                action = MainActivity.ACTION_SET_COLOR
            }

            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_MUTABLE
            } else {
                PendingIntent.FLAG_IMMUTABLE
            }

            val setColorPendingIntent = PendingIntent.getBroadcast(
                context,
                2,
                setColorIntent,
                flags
            )

            val setColorAction = NotificationCompat.Action.Builder(
                R.drawable.ic_set_color,
                context.getString(R.string.set_color),
                setColorPendingIntent
            ).addRemoteInput(remoteInput).build()

            val resetAction = NotificationCompat.Action.Builder(
                R.drawable.ic_reset,
                context.getString(R.string.reset_color),
                resetPendingIntent
            ).build()

            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content))
                .setContentIntent(contentPendingIntent)
                .setAutoCancel(true)
                .addAction(resetAction)
                .addAction(setColorAction)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)

        } catch (securityException: SecurityException) {
            // Обрабатываем случай, когда разрешение было отозвано
            Toast.makeText(context, "Ошибка безопасности при показе уведомления", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Ошибка при показе уведомления: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1
    }
}