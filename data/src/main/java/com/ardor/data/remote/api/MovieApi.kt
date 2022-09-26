package com.ardor.data.remote.api

import com.ardor.data.remote.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("")
    suspend fun getMovies(
        @Query("s") s : String,
        @Query("type") type : String?,
        @Query("page") page : Int?
    ) : Response<List<Movie>>
}