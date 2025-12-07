package com.ilya.examenpractico4aunidad.di

import android.content.Context
import androidx.room.Room
import com.ilya.examenpractico4aunidad.data.ApiPokemon
import com.ilya.examenpractico4aunidad.database.AppDatabase
import com.ilya.examenpractico4aunidad.database.PokemonDao
import com.ilya.examenpractico4aunidad.utils.Constants.Companion.BASE_URL
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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiPokemon(retrofit: Retrofit): ApiPokemon {
        return retrofit.create(ApiPokemon::class.java)
    }

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "pokemon_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesPokemonDao(database: AppDatabase): PokemonDao {
        return database.pokemonDao()
    }
}