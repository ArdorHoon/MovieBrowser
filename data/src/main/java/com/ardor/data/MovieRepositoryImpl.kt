package com.ardor.data

import com.ardor.data.remote.api.MovieService
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService
) {

}