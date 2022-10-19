package com.ardor.moviebroswer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ardor.domain.model.MovieEntity
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.usecase.DeleteFavoriteMovieUseCase
import com.ardor.domain.usecase.GetFavoriteMovieUseCase
import com.ardor.domain.usecase.GetMovieDetailUseCase
import com.ardor.domain.usecase.InsertFavoriteMovieUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val insertFavoriteMovieUseCase: InsertFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
) : BaseViewModel() {

    private val _detailMovie: MutableStateFlow<MovieEntity?> = MutableStateFlow(null)
    val detailMovie: StateFlow<MovieEntity?> = _detailMovie

    private lateinit var searchEntity: SearchEntity

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun onClickFavorite() {
        if (_isFavorite.value) {
            deleteFavorite(searchEntity.imdbID)

        } else {
            insertFavorite(searchEntity)
        }
    }


    private fun insertFavorite(item: SearchEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteMovieUseCase(item)
        }
    }

    private fun deleteFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovieUseCase(id)
        }
    }

    fun loadFavorite(imbId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMovieUseCase(imbId).collect {
                if (it != null) {
                    _isFavorite.value = true
                }
            }
        }
    }

    fun load(item: SearchEntity) {
        searchEntity = item
        viewModelScope.launch {
            getMovieDetailUseCase(item.imdbID).collect {
                _detailMovie.value = it
            }
        }
    }
}
