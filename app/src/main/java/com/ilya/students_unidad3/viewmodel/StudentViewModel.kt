package com.ilya.students_unidad3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.students_unidad3.data.StoreStudents
import com.ilya.students_unidad3.data.Students
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudentUiState(
    val students: List<Students> = emptyList(),
    val averageScore: Double = 0.0,
    val lowestScoringStudent: Students? = null,
    val top3ByGroup: Map<String, List<Students>> = emptyMap()
)

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val storeStudents = StoreStudents(application)
    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            storeStudents.getStudents.collect { storedList ->
                if (storedList.isEmpty() && _uiState.value.students.isEmpty()) {
                    loadInitialSampleData()
                } else {
                    _uiState.update { it.copy(students = storedList) }
                    calculateAllStatistics()
                }
            }
        }
    }

    private fun loadInitialSampleData() {
        val sampleStudents = listOf(
            Students(1, "Ilya", "Cortés", 5, "A", 150),
            Students(2, "Aurora", "Muñoz", 6, "B", 100),
            Students(3, "Yion", "Jaime", 5, "A", 78),
            Students(4, "Málaga", "Fernandez", 6, "B", 88),
            Students(5, "Toñito", "Calvo", 5, "A", 95),
            Students(6, "Moi", "Sanchez", 5, "A", 81),
            Students(7, "Rafa", "Ruiz^2", 6, "B", 76)
        )
        viewModelScope.launch {
            storeStudents.saveStudents(sampleStudents)
        }
    }

    fun addStudent(student: Students) {
        viewModelScope.launch {
            val currentList = _uiState.value.students
            val newId = (currentList.maxOfOrNull { it.id } ?: 0) + 1
            val studentWithCorrectId = student.copy(id = newId)

            val updatedList = currentList + studentWithCorrectId
            storeStudents.saveStudents(updatedList)
        }
    }

    fun deleteStudent(studentId: Int) {
        viewModelScope.launch {
            val updatedList = _uiState.value.students.filter { it.id != studentId }
            storeStudents.saveStudents(updatedList)
        }
    }

    fun updateStudent(updatedStudent: Students) {
        viewModelScope.launch {
            val updatedList = _uiState.value.students.map {
                if (it.id == updatedStudent.id) updatedStudent else it
            }
            storeStudents.saveStudents(updatedList)
        }
    }

    private fun calculateAllStatistics() {
        calculateAverage()
        calculateLowestScoringStudent()
        calculateTop3ByGroup()
    }

    private fun calculateAverage() {
        _uiState.update { currentState ->
            val average = if (currentState.students.isNotEmpty()) {
                currentState.students.map { it.score }.average()
            } else {
                0.0
            }
            currentState.copy(averageScore = average)
        }
    }

    private fun calculateLowestScoringStudent() {
        _uiState.update { currentState ->
            val lowest = currentState.students.minByOrNull { it.score }
            currentState.copy(lowestScoringStudent = lowest)
        }
    }

    private fun calculateTop3ByGroup() {
        _uiState.update { currentState ->
            val top3Map = currentState.students
                .groupBy { it.group }
                .mapValues { entry ->
                    entry.value.sortedByDescending { it.score }.take(3)
                }
            currentState.copy(top3ByGroup = top3Map)
        }
    }

    fun getStudentById(id: Int): Students? {
        return _uiState.value.students.find { it.id == id }
    }
}