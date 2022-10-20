package com.ardor.moviebroswer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.usecase.DeleteFavoriteMovieUseCase
import com.ardor.domain.usecase.GetAllFavoriteMoviesUseCase
import com.ardor.domain.usecase.GetMoviesUseCase
import com.ardor.domain.usecase.InsertFavoriteMovieUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
) : BaseViewModel() {

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

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

    private fun getMovies(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMoviesUseCase(title).onStart {
                _isLoading.value = true
            }.catch {
                getMoviesError()
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

    private fun getMoviesError() {
        event(Event.GetMoviesError("server error or network error"))
    }

    private fun sampleEvent1() {
        event(Event.SampleEvent1("aaa"))
    }

    private fun sampleEvent2() {
        event(Event.SampleEvent2(36))
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    sealed class Event {
        data class GetMoviesError(val text: String) : Event()
        data class SampleEvent1(val value: String) : Event()
        data class SampleEvent2(val value: Int) : Event()
    }
}