package com.ardor.moviebroswer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ardor.domain.model.SearchEntity
import com.ardor.domain.usecase.GetMoviesUseCase
import com.ardor.moviebroswer.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : BaseViewModel() {

    private val _searchResults : MutableStateFlow<List<SearchEntity>?> = MutableStateFlow(null)
    val searchResult : StateFlow<List<SearchEntity>?> =  _searchResults


    fun getMovies(title: String) {
        viewModelScope.launch {
            _searchResults.value = getMoviesUseCase(title).search
        }
    }
}