package com.ilya.examensegundaunidad.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "form_data")
data class FormData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val asunto: String,
    val email: String
)

@Dao
interface FormDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(formData: FormData)

    @Query("SELECT * FROM form_data ORDER BY id DESC")
    fun getAllData(): Flow<List<FormData>>
}

@Database(entities = [FormData::class], version = 1, exportSchema = false)
abstract class FormDatabase : RoomDatabase() {
    abstract fun formDao(): FormDao
}