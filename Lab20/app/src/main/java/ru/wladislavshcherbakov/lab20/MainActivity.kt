package ru.wladislavshcherbakov.lab20

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.wladislavshcherbakov.lab20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showNotification()
        } else {
            Toast.makeText(this, "Разрешение на уведомления не предоставлено", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = NotificationManager(this)

        setupUI()
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun setupUI() {
        binding.showNotificationButton.setOnClickListener {
            checkNotificationPermission()
        }
    }

    private fun handleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_RESET_COLOR -> resetBackgroundColor()
            ACTION_SET_COLOR -> {
                val color = intent.getStringExtra(EXTRA_COLOR)
                if (color != null) {
                    setBackgroundColor(color)
                }
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                showNotification()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // Для версий ниже Android 13 разрешение не требуется
            showNotification()
        }
    }

    private fun showNotification() {
        try {
            notificationManager.showNotification()
            Toast.makeText(this, "Уведомление показано", Toast.LENGTH_SHORT).show()
        } catch (e: SecurityException) {
            Toast.makeText(this, "Ошибка безопасности: нет разрешения на уведомления", Toast.LENGTH_SHORT).show()
            // Если произошла ошибка безопасности, запрашиваем разрешение снова
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка при показе уведомления: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetBackgroundColor() {
        binding.rootLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        binding.colorTextView.text = ""
        Toast.makeText(this, "Цвет сброшен", Toast.LENGTH_SHORT).show()
    }

    private fun setBackgroundColor(colorHex: String) {
        try {
            val color = Color.parseColor("#$colorHex")
            binding.rootLayout.setBackgroundColor(color)
            binding.colorTextView.text = getString(R.string.selected_color, "#$colorHex")
            Toast.makeText(this, "Цвет изменен на #$colorHex", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, R.string.invalid_color_format, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ACTION_RESET_COLOR = "ru.wladislavshcherbakov.lab20.ACTION_RESET_COLOR"
        const val ACTION_SET_COLOR = "ru.wladislavshcherbakov.lab20.ACTION_SET_COLOR"
        const val EXTRA_COLOR = "extra_color"
    }
}