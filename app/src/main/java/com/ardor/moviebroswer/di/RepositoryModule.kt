package com.ardor.moviebroswer.di

import com.ardor.data.MovieRepositoryImpl
import com.ardor.data.remote.api.MovieService
import com.ardor.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideMovieRepository(
        movieService: MovieService
    ) : MovieRepository = MovieRepositoryImpl(movieService)
}