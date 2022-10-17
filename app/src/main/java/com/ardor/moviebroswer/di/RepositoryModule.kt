package com.ardor.moviebroswer.di

import com.ardor.data.MovieRepositoryImpl
import com.ardor.data.remote.api.MovieService
import com.ardor.data.remote.source.MovieDatabase
import com.ardor.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun provideMovieRepository(
        movieService: MovieService,
        movieDatabase: MovieDatabase
    ) : MovieRepository = MovieRepositoryImpl(movieService, movieDatabase)
}