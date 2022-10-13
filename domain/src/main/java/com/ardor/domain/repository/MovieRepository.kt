package com.ardor.domain.repository

import com.ardor.domain.model.MovieEntity
import com.ardor.domain.model.SearchResultEntity

interface MovieRepository {
    suspend fun getSearchResults(title: String): SearchResultEntity
    suspend fun getMovieDetail(imbId : String) : MovieEntity
}