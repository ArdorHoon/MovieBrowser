package com.ardor.data.remote.api

import com.ardor.data.API_KEY
import com.ardor.data.remote.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("?i=tt3896198&apikey=${API_KEY}")
    suspend fun getMovies(
        @Query("s") s : String,
        @Query("type") type : String?,
        @Query("page") page : Int?
    ) : Response<List<Movie>>
}