package com.ardor.data

import com.ardor.data.remote.api.MovieService
import com.ardor.domain.model.MovieEntity
import com.ardor.domain.model.ResultEntity
import com.ardor.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRepository {
    override suspend fun getMovies(title: String): ResultEntity {
        val temp = movieService.getMovies(title)
        return ResultEntity(
            temp.Search?.map {
                MovieEntity(
                    it.Title,
                    it.Year ?: "",
                    it.Poster
                )
            },
            temp.totalResults
        )
    }
}