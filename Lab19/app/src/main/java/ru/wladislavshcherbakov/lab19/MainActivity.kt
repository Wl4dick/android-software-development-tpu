package ru.wladislavshcherbakov.lab19

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var getContactsButton: Button
    private lateinit var contactsTextView: TextView

    // Лаунчер для запроса разрешения
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Разрешение получено
            loadContacts()
        } else {
            // Пользователь отказал
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_CONTACTS
                )
            ) {
                // Пользователь выбрал "Больше не спрашивать"
                showPermissionPermanentlyDeniedDialog()
            } else {
                // Простой отказ
                showPermissionDeniedDialog()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        getContactsButton = findViewById(R.id.getContactsButton)
        contactsTextView = findViewById(R.id.contactsTextView)
    }

    private fun setupClickListeners() {
        getContactsButton.setOnClickListener {
            checkPermissionAndLoadContacts()
        }
    }

    private fun checkPermissionAndLoadContacts() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Разрешение уже есть
                loadContacts()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) -> {
                // Нужно показать объяснение
                showPermissionExplanationDialog()
            }

            else -> {
                // Запрашиваем разрешение
                requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private fun showPermissionExplanationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required_title))
            .setMessage(getString(R.string.permission_explanation))
            .setPositiveButton(getString(R.string.grant_permission)) { dialog, _ ->
                dialog.dismiss()
                requestPermissionLauncher.launch(android.Manifest.permission.READ_CONTACTS)
            }
            .setNegativeButton(getString(R.string.deny_permission)) { dialog, _ ->
                dialog.dismiss()
                showPermissionDeniedDialog()
            }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required_title))
            .setMessage(getString(R.string.permission_required_message))
            .setPositiveButton(getString(R.string.open_settings)) { dialog, _ ->
                dialog.dismiss()
                openAppSettings()
            }
            .setNegativeButton(getString(R.string.no_thanks)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showPermissionPermanentlyDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.permission_required_title))
            .setMessage("Вы запретили запрос разрешения. Для предоставления разрешения необходимо зайти в настройки приложения.")
            .setPositiveButton(getString(R.string.open_settings)) { dialog, _ ->
                dialog.dismiss()
                openAppSettings()
            }
            .setNegativeButton(getString(R.string.no_thanks)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    @SuppressLint("Range")
    private fun loadContacts() {
        try {
            val contacts = getContacts()
            displayContacts(contacts)
        } catch (e: SecurityException) {
            contactsTextView.text = "Ошибка: нет разрешения на чтение контактов"
        } catch (e: Exception) {
            contactsTextView.text = "Ошибка при загрузке контактов: ${e.message}"
        }
    }

    @SuppressLint("Range")
    private fun getContacts(): List<String> {
        val result = mutableListOf<String>()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use { cur ->
            val nameColumn = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            while (cur.moveToNext()) {
                val name = cur.getString(nameColumn)
                if (!name.isNullOrBlank()) {
                    result.add(name)
                }
            }
        }

        return result
    }

    private fun displayContacts(contacts: List<String>) {
        if (contacts.isEmpty()) {
            contactsTextView.text = "Контакты не найдены"
        } else {
            val contactsText = StringBuilder().apply {
                append(getString(R.string.success_title))
                append("\n")
                append(getString(R.string.contacts_retrieved))
                append("\n\n")
                contacts.forEachIndexed { index, contact ->
                    append("${index + 1}. $contact\n")
                }
            }
            contactsTextView.text = contactsText.toString()
        }
    }
}