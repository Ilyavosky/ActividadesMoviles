package com.ilya.examenpractico4aunidad.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilya.examenpractico4aunidad.models.Character

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}