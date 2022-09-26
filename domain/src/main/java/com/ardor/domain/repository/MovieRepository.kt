package com.ardor.domain.repository

import com.ardor.domain.model.MovieEntity

interface MovieRepository {
    fun getMovies(title : String) : List<MovieEntity>
}