package com.ilya.examenpractico4aunidad.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ilya.examenpractico4aunidad.models.Character
import com.ilya.examenpractico4aunidad.repositories.CharactersRepository
import kotlinx.coroutines.delay

class CharacterDataSource(
    private val repo: CharactersRepository
): PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val nextPageNumber = params.key ?: 1
            delay(1000)
            val response = repo.getAllCharacters(nextPageNumber)
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}