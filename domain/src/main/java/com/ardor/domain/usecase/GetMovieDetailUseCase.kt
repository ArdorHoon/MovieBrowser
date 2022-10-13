package com.ardor.domain.usecase

import com.ardor.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(imbId: String) = movieRepository.getMovieDetail(imbId)
}