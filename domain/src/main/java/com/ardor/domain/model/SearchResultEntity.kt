package com.ardor.domain.model

data class SearchResultEntity(
    val search: List<SearchEntity>?,
    val totalResult: String?
)
