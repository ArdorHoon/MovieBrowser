package com.ardor.domain.usecase

import com.ardor.domain.model.SearchEntity
import com.ardor.domain.repository.MovieRepository
import javax.inject.Inject

class InsertFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(item: SearchEntity) = movieRepository.insertFavorite(item)
}