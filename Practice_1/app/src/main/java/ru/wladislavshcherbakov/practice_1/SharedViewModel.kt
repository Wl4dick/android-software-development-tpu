package ru.wladislavshcherbakov.practice_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.wladislavshcherbakov.practice_1.levels_service.Level

class SharedViewModel : ViewModel() {
    private val _selectedLevel = MutableLiveData<Level>()
    val selectedLevel: LiveData<Level> = _selectedLevel

    fun selectLevel(level: Level) {
        _selectedLevel.value = level
    }
}