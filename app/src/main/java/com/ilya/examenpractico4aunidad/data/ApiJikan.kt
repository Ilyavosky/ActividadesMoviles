package com.ilya.examenpractico4aunidad.data

import com.ilya.examenpractico4aunidad.models.JikanCharacterResponse
import com.ilya.examenpractico4aunidad.models.JikanCharactersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiJikan {

    @GET("characters")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25,
        @Query("order_by") orderBy: String = "favorites",
        @Query("sort") sort: String = "desc"
    ): Response<JikanCharactersResponse>

    @GET("characters/{id}/full")
    suspend fun getCharacterById(
        @Path("id") id: String
    ): Response<JikanCharacterResponse>

    @GET("characters")
    suspend fun searchCharacters(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): Response<JikanCharactersResponse>
}