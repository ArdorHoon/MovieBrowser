package com.ardor.moviebroswer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.usecase.DeleteFavoriteUseCase
import com.ardor.domain.usecase.GetAllFavoritesUseCase
import com.ardor.domain.usecase.GetMoviesUseCase
import com.ardor.domain.usecase.InsertFavoriteUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : BaseViewModel() {

    private val _searchResults: MutableStateFlow<List<SearchEntity>?> = MutableStateFlow(null)
    val searchResult: StateFlow<List<SearchEntity>?> = _searchResults

    private val _favoriteMovies: MutableStateFlow<List<SearchEntity>?> = MutableStateFlow(null)
    val favoriteMovies: StateFlow<List<SearchEntity>?> = _favoriteMovies

    fun getMovies(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchResults.value = getMoviesUseCase(title).search
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFavoritesUseCase().collect {
                _favoriteMovies.value = it
            }
        }
    }

}