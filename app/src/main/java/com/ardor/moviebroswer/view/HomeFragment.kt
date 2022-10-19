package com.ardor.moviebroswer.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ardor.domain.model.SearchEntity
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.base.BaseFragment
import com.ardor.moviebroswer.databinding.FragmentHomeBinding
import com.ardor.moviebroswer.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment() :
    BaseFragment<FragmentHomeBinding, MovieViewModel>(R.layout.fragment_home),
    MovieAdapter.ItemClickListener {

    override val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.itemClickListener = this@HomeFragment

        viewModel.getMovies("frozen")
    }

    override fun moveDetailPage(result: SearchEntity) {
        val action = HomeFragmentDirections.actionNavigationHomeToMovieDetailFragment(result)
        findNavController().navigate(action)
    }
}