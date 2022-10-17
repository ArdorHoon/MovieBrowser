package com.ardor.moviebroswer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ardor.domain.usecase.GetMovieDetailUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : BaseViewModel() {

    fun load(imbId: String) {
        viewModelScope.launch {
            val data = getMovieDetailUseCase(imbId)
        }
    }
}
