package ru.wladislavshcherbakov.lab22

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlin.math.*

class MainActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private lateinit var tvStatus: TextView
    private lateinit var tvDistance: TextView

    private var targetLatitude: Double = 0.0
    private var targetLongitude: Double = 0.0
    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            currentLatitude = location.latitude
            currentLongitude = location.longitude
            updateDistance()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupLocationManager()
        requestPermissions()
    }

    private fun initViews() {
        tvStatus = findViewById(R.id.tvStatus)
        tvDistance = findViewById(R.id.tvDistance)

        findViewById<Button>(R.id.btnNewPoint).setOnClickListener {
            generateNewTarget()
        }

        findViewById<Button>(R.id.btnSettings).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Инициализируем начальную цель
        generateNewTarget()
    }

    private fun setupLocationManager() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
    }

    private fun requestPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_CODE
            )
        } else {
            startLocationUpdates()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L,
                    10f,
                    locationListener
                )
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    1000L,
                    10f,
                    locationListener
                )

                // Получаем последнее известное местоположение
                val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                lastLocation?.let {
                    currentLatitude = it.latitude
                    currentLongitude = it.longitude
                    updateDistance()
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private fun generateNewTarget() {
        // Если текущее местоположение неизвестно, используем приблизительные координаты
        if (currentLatitude == 0.0 && currentLongitude == 0.0) {
            currentLatitude = 56.484602  // Примерные координаты
            currentLongitude = 84.947465
        }

        val randomOffset = 0.02 * (Math.random() - 0.5) // ±0.02 градуса ~ 2.5 км
        targetLatitude = currentLatitude + randomOffset
        targetLongitude = currentLongitude + randomOffset

        tvStatus.text = getString(R.string.status_searching)
        tvStatus.setTextColor(getColor(R.color.color_blue))
        updateDistance()
    }

    private fun updateDistance() {
        if (targetLatitude == 0.0 && targetLongitude == 0.0) return

        val distance = calculateDistance(
            currentLatitude,
            currentLongitude,
            targetLatitude,
            targetLongitude
        )

        tvDistance.text = "${getString(R.string.distance_prefix)} ${"%.1f".format(distance)} м"

        if (distance < 100) {
            tvStatus.text = getString(R.string.status_found)
            tvStatus.setTextColor(getColor(R.color.color_green))
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // метров

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        try {
            locationManager.removeUpdates(locationListener)
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_CODE = 1001
    }
}