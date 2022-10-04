package com.ardor.data

import com.ardor.data.remote.api.MovieService
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.model.SearchResultEntity
import com.ardor.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getSearchResults(title: String): SearchResultEntity {
        val temp = movieService.getSearchResults(title, API_KEY, 1)
        return SearchResultEntity(
            temp.Search?.map {
                SearchEntity(
                    it.Title,
                    it.Year ?: "",
                    it.Poster,
                    it.imdbID
                )
            },
            temp.totalResults
        )
    }
}