package com.ilya.examensegundaunidad.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.examensegundaunidad.data.FormDao
import com.ilya.examensegundaunidad.data.FormData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val formDao: FormDao
) : ViewModel() {

    val allFormData: StateFlow<List<FormData>> = formDao.getAllData()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveData(nombre: String, asunto: String, email: String) {
        viewModelScope.launch {
            if (nombre.isNotBlank() && asunto.isNotBlank() && email.isNotBlank()) {
                formDao.insert(FormData(
                    nombre = nombre,
                    asunto = asunto,
                    email = email
                ))
            }
        }
    }
}