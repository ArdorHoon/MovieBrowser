package com.ardor.domain.repository

import com.ardor.domain.model.SearchResultEntity

interface MovieRepository {
    suspend fun getSearchResults(title: String): SearchResultEntity
}