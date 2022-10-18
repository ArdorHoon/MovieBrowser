package com.ardor.data

import com.ardor.data.remote.api.MovieService
import com.ardor.data.remote.model.Search
import com.ardor.data.remote.source.MovieDatabase
import com.ardor.domain.model.MovieEntity
import com.ardor.domain.model.RatingEntity
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.model.SearchResultEntity
import com.ardor.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDatabase: MovieDatabase
) : MovieRepository {
    override suspend fun getSearchResults(title: String): Flow<SearchResultEntity> {
        return flow {
            val temp = movieService.getSearchResults(title, API_KEY, 1)
            val data = SearchResultEntity(
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
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMovieDetail(imbId: String): Flow<MovieEntity?> {
        return flow {
            val temp = movieService.getMovieDetails(API_KEY, imbId)
            val data = MovieEntity(
                Actors = temp.Actors,
                Country = temp.Country,
                Director = temp.Director,
                Poster = temp.Poster,
                Genre = temp.Genre,
                Rated = temp.Rated,
                Ratings = temp.Ratings?.map { RatingEntity(it.Source, it.Value) },
                Writer = temp.Writer,
                Year = temp.Year
            )
            emit(data)
        }
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