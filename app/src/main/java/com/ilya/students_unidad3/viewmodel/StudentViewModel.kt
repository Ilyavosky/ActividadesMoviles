package com.ilya.students_unidad3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class StudentViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudentUiState())
    val uiState: StateFlow<StudentUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val sampleStudents = listOf(
                Students(1, "Ilya", "Cortés", 5, "A", 150),
                Students(2, "Aurora", "Muñoz", 6, "B", 100),
                Students(3, "Yion", "Jaime", 5, "A", 78),
                Students(4, "Málaga", "Fernandez", 6, "B", 88),
                Students(5, "Toñito", "Calvo", 5, "A", 95),
                Students(6, "Moi", "Sanchez", 5, "A", 81),
                Students(7, "Rafa", "Ruiz^2", 6, "B", 76)
            )
            _uiState.value = _uiState.value.copy(students = sampleStudents)
            calculateAllStatistics()
        }
    }

    fun addStudent(student: Students) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedList = currentState.students + student
                currentState.copy(students = updatedList)
            }
            calculateAllStatistics()
        }
    }

    fun deleteStudent(studentId: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedList = currentState.students.filter { it.id != studentId }
                currentState.copy(students = updatedList)
            }
            calculateAllStatistics()
        }
    }

    fun updateStudent(updatedStudent: Students) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val updatedList = currentState.students.map {
                    if (it.id == updatedStudent.id) updatedStudent else it
                }
                currentState.copy(students = updatedList)
            }
            calculateAllStatistics()
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