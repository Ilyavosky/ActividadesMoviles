package com.ilya.examensegundaunidad.di

import android.content.Context
import androidx.room.Room
import com.ilya.examensegundaunidad.data.FormDatabase
import com.ilya.examensegundaunidad.data.StoreDarkMode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStoreDarkMode(@ApplicationContext context: Context): StoreDarkMode {
        return StoreDarkMode(context)
    }

    @Provides
    @Singleton
    fun provideFormDatabase(@ApplicationContext context: Context): FormDatabase {
        return Room.databaseBuilder(
            context,
            FormDatabase::class.java,
            "form_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFormDao(database: FormDatabase) = database.formDao()
}
