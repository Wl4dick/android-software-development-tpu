package ru.wladislavshcherbakov.lab21

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity(), TimerService.TimerCallback {

    private lateinit var editTextMinutes: EditText
    private lateinit var editTextSeconds: EditText
    private lateinit var buttonStartStop: Button
    private lateinit var textViewStatus: TextView

    private var timerService: TimerService? = null
    private var isBound = false

    // Регистрируем контракт для запроса разрешения на уведомления
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Разрешение получено, можно запускать таймер
            startTimer()
        } else {
            // Разрешение не получено, показываем объяснение
            showNotificationPermissionExplanation()
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.TimerBinder
            timerService = binder.getService()
            isBound = true
            timerService?.setCallback(this@MainActivity)
            updateUIFromService()
        }

        override fun onServiceDisconnected(className: ComponentName) {
            isBound = false
            timerService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()

        val intent = Intent(this, TimerService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private fun initViews() {
        editTextMinutes = findViewById(R.id.editTextMinutes)
        editTextSeconds = findViewById(R.id.editTextSeconds)
        buttonStartStop = findViewById(R.id.buttonStartStop)
        textViewStatus = findViewById(R.id.textViewStatus)
    }

    private fun setupClickListeners() {
        buttonStartStop.setOnClickListener {
            if (timerService?.isTimerRunning() == true) {
                timerService?.stopTimer()
                resetUI()
            } else {
                // Проверяем разрешение на уведомления перед запуском таймера
                checkNotificationPermission()
            }
        }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Разрешение уже есть, запускаем таймер
                startTimer()
            } else {
                // Запрашиваем разрешение
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // На версиях ниже Android 13 разрешение не требуется
            startTimer()
        }
    }

    private fun startTimer() {
        val minutes = editTextMinutes.text.toString().toIntOrNull() ?: 0
        val seconds = editTextSeconds.text.toString().toIntOrNull() ?: 0

        if (minutes > 0 || seconds > 0) {
            val intent = Intent(this, TimerService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
            timerService?.startTimer(minutes, seconds)
            updateUIOnStart()
        } else {
            Toast.makeText(this, "Введите время", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNotificationPermissionExplanation() {
        AlertDialog.Builder(this)
            .setTitle("Разрешение на уведомления")
            .setMessage("Для работы таймера в фоновом режиме необходимо разрешение на показ уведомлений. Без этого уведомление таймера не будет отображаться.")
            .setPositiveButton("Понятно") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateUIFromService() {
        timerService?.let { service ->
            if (service.isTimerRunning()) {
                updateUIOnStart()
                onTimerUpdate(service.getTimeLeft())
            } else {
                resetUI()
            }
        }
    }

    private fun updateUIOnStart() {
        runOnUiThread {
            buttonStartStop.setText(R.string.stop)
            editTextMinutes.isEnabled = false
            editTextSeconds.isEnabled = false
        }
    }

    private fun resetUI() {
        runOnUiThread {
            buttonStartStop.setText(R.string.start)
            editTextMinutes.isEnabled = true
            editTextSeconds.isEnabled = true
            textViewStatus.setText(R.string.timer_ready)
        }
    }

    override fun onTimerUpdate(timeLeft: Long) {
        runOnUiThread {
            val minutes = timeLeft / 60
            val seconds = timeLeft % 60
            textViewStatus.text = String.format("%d:%02d", minutes, seconds)
        }
    }

    override fun onTimerFinished() {
        runOnUiThread {
            resetUI()
            Toast.makeText(this, "Таймер завершен", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            timerService?.setCallback(null)
            unbindService(connection)
            isBound = false
        }
    }
}