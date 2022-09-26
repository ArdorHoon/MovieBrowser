package com.ardor.domain.repository

import com.ardor.domain.model.ResultEntity

interface MovieRepository {
    suspend fun getMovies(title: String): ResultEntity
}