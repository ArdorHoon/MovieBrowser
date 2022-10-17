package com.ardor.data

import com.ardor.data.remote.api.MovieService
import com.ardor.data.remote.model.Search
import com.ardor.data.remote.source.MovieDatabase
import com.ardor.domain.model.MovieEntity
import com.ardor.domain.model.RatingEntity
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.model.SearchResultEntity
import com.ardor.domain.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase
) : MovieRepository {
    override suspend fun getSearchResults(title: String): SearchResultEntity {
        val temp = movieService.getSearchResults(title, API_KEY, 1)
        return SearchResultEntity(
            temp.Search?.map {
                SearchEntity(
                    it.Title,
                    it.Year ?: "",
                    it.Poster,
                    it.imdbID
                )
            },
            temp.totalResults
        )
    }

    override suspend fun getMovieDetail(imbId: String): MovieEntity {
        val data = movieService.getMovieDetails(API_KEY, imbId)
        return MovieEntity(
            Actors = data.Actors,
            Country = data.Country,
            Director = data.Director,
            Poster = data.Poster,
            Genre = data.Genre,
            Rated = data.Rated,
            Ratings = data.Ratings?.map { RatingEntity(it.Source, it.Value) },
            Writer = data.Writer,
            Year = data.Year
        )
    }

    override suspend fun getAllFavorites(): Flow<List<SearchEntity>> {
        return movieDatabase.movieDao().getAll().mapLatest {
            val data = it.map {
                SearchEntity(
                    title = it.Title,
                    year = it.Year ?: "",
                    poster = it.Poster,
                    imdbID = it.imdbID
                )
            }.toList()
            data
        }
    }

    override suspend fun insertFavorite(item: SearchEntity) {
        val temp = Search(
            Poster = item.poster,
            Title = item.title,
            Type = "",
            Year = item.year,
            imdbID = item.imdbID
        )
        movieDatabase.movieDao().insert(temp)

    }

    override suspend fun deleteFavorite(id: String) {
        movieDatabase.movieDao().delete(id)
    }

}