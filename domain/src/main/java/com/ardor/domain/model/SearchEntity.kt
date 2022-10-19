package com.ardor.domain.model

import java.io.Serializable

data class SearchEntity(
    val title: String,
    val year: String,
    val poster: String,
    val imdbID: String = "",
) : Serializable
