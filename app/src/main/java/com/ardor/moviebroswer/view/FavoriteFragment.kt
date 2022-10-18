package com.ardor.moviebroswer.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ardor.domain.model.SearchEntity
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.base.BaseFragment
import com.ardor.moviebroswer.databinding.FragmentFavoriteBinding
import com.ardor.moviebroswer.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding, MovieViewModel>(R.layout.fragment_favorite),
    MovieAdapter.ItemClickListener {

    override val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.itemClickListener = this@FavoriteFragment

    }

    override fun moveDetailPage(result: SearchEntity) {
        val action =
            FavoriteFragmentDirections.actionNavigationFavoriteToMovieDetailFragment(result.imdbID)
        findNavController().navigate(action)
    }
}