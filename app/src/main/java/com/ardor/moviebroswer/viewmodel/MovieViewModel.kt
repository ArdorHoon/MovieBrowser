package com.ardor.moviebroswer.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ardor.domain.usecase.GetMoviesUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {

    fun getMovies(title: String) {
        viewModelScope.launch {
            val temp = getMoviesUseCase(title)
            Log.d("testing", temp.toString())
        }

    }
}