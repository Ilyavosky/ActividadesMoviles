package com.ilya.students_unidad3.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreStudents(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("StudentsData")
        val STUDENTS_LIST = stringPreferencesKey("students_list")
    }

    val getStudents: Flow<List<Students>> = context.dataStore.data
        .map { preferences ->
            val studentsString = preferences[STUDENTS_LIST] ?: ""
            if (studentsString.isNotEmpty()) {
                studentsString.split("|").map { studentStr ->
                    val parts = studentStr.split(",")
                    Students(
                        id = parts[0].toInt(),
                        name = parts[1],
                        lastName = parts[2],
                        scolarYear = parts[3].toInt(),
                        group = parts[4],
                        score = parts[5].toInt()
                    )
                }
            } else {
                emptyList()
            }
        }

    suspend fun saveStudents(students: List<Students>) {
        val studentsString = students.joinToString(separator = "|") { student ->
            "${student.id},${student.name},${student.lastName},${student.scolarYear},${student.group},${student.score}"
        }
        context.dataStore.edit { preferences ->
            preferences[STUDENTS_LIST] = studentsString
        }
    }
}