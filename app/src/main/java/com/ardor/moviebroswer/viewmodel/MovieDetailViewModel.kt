package com.ardor.moviebroswer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ardor.domain.model.MovieEntity
import com.ardor.domain.usecase.GetMovieDetailUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : BaseViewModel() {

    private val _detailMovie: MutableStateFlow<MovieEntity?> = MutableStateFlow(null)
    val detailMovie: StateFlow<MovieEntity?> = _detailMovie

    fun load(imbId: String) {
        viewModelScope.launch {
            getMovieDetailUseCase(imbId).collect {
                _detailMovie.value = it
            }
        }
    }
}
