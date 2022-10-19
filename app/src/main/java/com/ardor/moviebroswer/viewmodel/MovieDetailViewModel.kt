package com.ardor.moviebroswer.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.zip
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

    private var searchEntity: SearchEntity? = null

    private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun loadFavorite(imbId: String){
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMovieUseCase(imbId).collect {
                if (it != null) {
                    searchEntity = it
                    _isFavorite.value = true
                }
            }
        }
    }

    fun load(imbId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase(imbId).collect {
                _detailMovie.value = it
            }
        }
    }
}
