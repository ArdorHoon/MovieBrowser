package com.ardor.domain.usecase

import com.ardor.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllFavoriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke() = movieRepository.getAllFavorites()
}