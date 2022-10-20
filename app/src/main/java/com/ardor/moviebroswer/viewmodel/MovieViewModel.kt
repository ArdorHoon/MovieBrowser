package com.ardor.moviebroswer.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.usecase.*
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase
) : BaseViewModel() {

    private val _searchResults: MutableStateFlow<List<SearchEntity>?> = MutableStateFlow(null)
    val searchResult: StateFlow<List<SearchEntity>?> = _searchResults

    private val _favoriteMovies: MutableStateFlow<List<SearchEntity>?> = MutableStateFlow(null)
    val favoriteMovies: StateFlow<List<SearchEntity>?> = _favoriteMovies

    private val _movieTitle: MutableStateFlow<String?> = MutableStateFlow(null)
    val movieTitle: StateFlow<String?> = _movieTitle

    private val _movieYear: MutableStateFlow<String?> = MutableStateFlow(null)
    val movieYear: StateFlow<String?> = _movieYear

    private val _favoriteMovieTitle: MutableStateFlow<String?> = MutableStateFlow(null)
    val favoriteMovieTitle: StateFlow<String?> = _favoriteMovieTitle

    private val _favoriteMovieYear: MutableStateFlow<String?> = MutableStateFlow(null)
    val favoriteMovieYear: StateFlow<String?> = _favoriteMovieYear

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val searchMovie = { query: String? ->
        if (query != null) {
            getMovies(query)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMovies("frozen")
            getAllFavoriteMoviesUseCase().catch {
                //handling error
            }.collect {
                _favoriteMovies.value = it
            }
        }
    }

    fun insertFavorite(item: SearchEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteMovieUseCase(item)
        }
    }

    fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovieUseCase(id)
        }
    }

    fun setTitleAndMovie(position: Int) {
        _movieTitle.value = _searchResults.value?.get(position)?.title
        _movieYear.value = _searchResults.value?.get(position)?.year
    }

    fun emptyTitleAndMovie() {
        _movieTitle.value = "No Search Data\nPlease search again"
        _movieYear.value = ""
    }

    fun setFavoriteTitleAndMovie(position: Int) {
        _favoriteMovieYear.value = _favoriteMovies.value?.get(position)?.year
        _favoriteMovieTitle.value = _favoriteMovies.value?.get(position)?.title
    }

    suspend fun checkCurrentFavorite(imbId: String): Boolean {
        val check = viewModelScope.async(Dispatchers.IO) {
            var temp = false
            getFavoriteMovieUseCase(imbId).collect {
                if (it != null) {
                    temp = true
                }
            }
            temp
        }.await()
        return check
    }

    private fun getMovies(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase(title).onStart {
                _isLoading.value = true
            }.catch {
                Log.d("testing", "onCatch")
                //handling error
            }.collect {
                _isLoading.value = false

                if (it.search.isNullOrEmpty()) {
                    val emptyItem: List<SearchEntity> = arrayListOf<SearchEntity>().apply {
                        this.add(
                            SearchEntity(
                                title = "No Search Data\nPlease search again",
                                year = "",
                                poster = "",
                                imdbID = "",
                                favorite = false
                            )
                        )
                    }
                    _searchResults.value = emptyItem
                } else {
                    _searchResults.value = it.search
                }
            }
        }
    }
}