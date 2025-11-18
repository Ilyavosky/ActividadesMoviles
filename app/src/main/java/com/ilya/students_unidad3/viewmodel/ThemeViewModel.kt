package com.ilya.students_unidad3.viewmodel.ThemeViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.students_unidad3.data.StoreDarkMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = StoreDarkMode(application)

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.getDarkMode.collect { isDark ->
                _isDarkMode.value = isDark
            }
        }
    }

    fun setDarkMode(isDark: Boolean) {
        viewModelScope.launch {
            dataStore.setDarkMode(isDark)
        }
    }
}