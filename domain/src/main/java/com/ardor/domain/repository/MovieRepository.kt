package com.ardor.domain.repository

import com.ardor.domain.model.MovieEntity
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.model.SearchResultEntity
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getSearchResults(title: String): Flow<SearchResultEntity>
    suspend fun getMovieDetail(imbId : String) : Flow<MovieEntity?>
    suspend fun getAllFavorites() : Flow<List<SearchEntity>>
    suspend fun insertFavorite(item : SearchEntity)
    suspend fun deleteFavorite(id : String)
    suspend fun getFavorite(id : String) : Flow<SearchEntity?>
}