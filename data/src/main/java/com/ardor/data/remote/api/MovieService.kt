package com.ardor.data.remote.api

import com.ardor.data.remote.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("?type=movie")
    suspend fun getSearchResults(
        @Query(value = "s") s: String,
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "page") pageIndex: Int
    ): SearchResult

    @GET("?plot=full")
    suspend fun getMovieDetails(
        @Query(value = "apiKey") apiKey: String,
        @Query(value = "i") imdbId: String
    )
}