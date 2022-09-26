package com.ardor.data.remote.api

import com.ardor.data.API_KEY
import com.ardor.data.remote.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("?i=tt3896198&apikey=${API_KEY}")
    suspend fun getMovies(
        @Query("s") s: String,
    ): SearchResult
}