package com.ardor.moviebroswer.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.base.BaseFragment
import com.ardor.moviebroswer.databinding.FragmentMovieDetailBinding
import com.ardor.moviebroswer.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(R.layout.fragment_movie_detail) {

    override val viewModel: MovieDetailViewModel by viewModels()
    private val movieDetailArgs by navArgs<MovieDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this@MovieDetailFragment
        binding.viewmodel = viewModel
        setBackButton()
        movieDetailArgs.searchEntity?.let {
            viewModel.loadFavorite(it.imdbID)
            viewModel.load(it)
        }
    }

    private fun setBackButton() {
        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}