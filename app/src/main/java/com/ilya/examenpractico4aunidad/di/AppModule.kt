package com.ilya.examenpractico4aunidad.di

import android.content.Context
import androidx.room.Room
import com.ilya.examenpractico4aunidad.data.ApiJikan
import com.ilya.examenpractico4aunidad.database.AppDatabase
import com.ilya.examenpractico4aunidad.database.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiJikan(retrofit: Retrofit): ApiJikan {
        return retrofit.create(ApiJikan::class.java)
    }

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "characters_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesCharacterDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }
}