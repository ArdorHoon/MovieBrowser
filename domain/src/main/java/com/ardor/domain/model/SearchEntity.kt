package com.ardor.domain.model

data class SearchEntity(
    val title : String,
    val year : String,
    val poster : String,
    val imdbID: String? = "",
)
