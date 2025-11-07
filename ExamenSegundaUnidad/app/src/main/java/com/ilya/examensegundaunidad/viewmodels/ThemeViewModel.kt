package com.ilya.examensegundaunidad.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.examensegundaunidad.data.StoreDarkMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val storeDarkMode: StoreDarkMode
) : ViewModel() {

    val isDarkMode: StateFlow<Boolean> = storeDarkMode.getDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun saveDarkMode(value: Boolean) {
        viewModelScope.launch {
            storeDarkMode.saveDarkMode(value)
        }
    }
}