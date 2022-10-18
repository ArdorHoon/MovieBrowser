package com.ardor.domain.model

data class MovieEntity(
    val Actors: String = "",
    val Country: String? = "",
    val Director: String = "",
    val Genre: String? = "",
    val Language: String? = "",
    val Metascore: String? = "",
    val Poster: String? = "",
    val Rated: String? = "",
    val Plot: String? = "",
    val Awards: String? = "",
    val Ratings: List<RatingEntity>? = listOf(),
    val Title: String? = "",
    val Writer: String? = "",
    val imdbRating: String? = "",
    val Year: String? = "",
) {
    fun getRating() = "$imdbRating / 10"
}
