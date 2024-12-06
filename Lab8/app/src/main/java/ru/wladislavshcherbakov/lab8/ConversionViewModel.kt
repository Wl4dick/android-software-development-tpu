package ru.wladislavshcherbakov.lab8
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ConversionViewModel : ViewModel(){
    private val _meters = MutableLiveData<String>()
    val meters: LiveData<String> get() = _meters

    private val _inches = MutableLiveData<String>()
    val inches: LiveData<String> get() = _inches

    private val _yards = MutableLiveData<String>()
    val yards: LiveData<String> get() = _yards

    private val _feet = MutableLiveData<String>()
    val feet: LiveData<String> get() = _feet

    private var isUpdating = false

    fun updateMeters(value: String) {
        if (!isUpdating) {
            isUpdating = true
            _meters.value = value
            convertFromMeters(value)
            isUpdating = false
        }
    }

    private fun convertFromMeters(meterValue: String) {
        val meters = meterValue.toDoubleOrNull() ?: return

        val inch = meters * 39.3701
        val yard = meters * 1.09361
        val foot = meters * 3.28084

        _inches.value = inch.toString()
        _yards.value = yard.toString()
        _feet.value = foot.toString()
    }

    // Аналогичные методы для updateInches, updateYards, updateFeet
    fun updateInches(value: String) {
        if (!isUpdating) {
            isUpdating = true
            _inches.value = value
            convertFromInches(value)
            isUpdating = false
        }
    }

    private fun convertFromInches(inchValue: String) {
        val inches = inchValue.toDoubleOrNull() ?: return

        val meters = inches / 39.3701
        val yard = inches / 36
        val foot = inches / 12

        _meters.value = meters.toString()
        _yards.value = yard.toString()
        _feet.value = foot.toString()
    }

    fun updateYards(value: String) {
        if (!isUpdating) {
            isUpdating = true
            _yards.value = value
            convertFromYards(value)
            isUpdating = false
        }
    }

    private fun convertFromYards(yardValue: String) {
        val yards = yardValue.toDoubleOrNull() ?: return

        val meters = yards / 1.09361
        val inch = yards * 36
        val foot = yards * 3

        _meters.value = meters.toString()
        _inches.value = inch.toString()
        _feet.value = foot.toString()
    }

    fun updateFeet(value: String) {
        if (!isUpdating) {
            isUpdating = true
            _feet.value = value
            convertFromFeet(value)
            isUpdating = false
        }
    }

    private fun convertFromFeet(footValue: String) {
        val feet = footValue.toDoubleOrNull() ?: return

        val meters = feet / 3.28084
        val inch = feet * 12
        val yard = feet / 3

        _meters.value = meters.toString()
        _inches.value = inch.toString()
        _yards.value = yard.toString()
    }
}