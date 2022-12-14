package com.ardor.moviebroswer.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ardor.domain.model.SearchEntity
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.base.BaseFragment
import com.ardor.moviebroswer.core.extension.repeatOnStarted
import com.ardor.moviebroswer.databinding.FragmentHomeBinding
import com.ardor.moviebroswer.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.ardor.moviebroswer.viewmodel.MovieViewModel.Event

@AndroidEntryPoint
class HomeFragment() :
    BaseFragment<FragmentHomeBinding, MovieViewModel>(R.layout.fragment_home),
    MovieAdapter.ItemClickListener {

    override val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.itemClickListener = this@HomeFragment

        repeatOnStarted {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }

    }

    override fun moveDetailPage(result: SearchEntity) {
        val action = HomeFragmentDirections.actionNavigationHomeToMovieDetailFragment(result)
        findNavController().navigate(action)
    }

    private fun handleEvent(event: Event) = when (event) {
        is Event.GetMoviesError -> Toast.makeText(requireContext(), event.text, Toast.LENGTH_SHORT).show()
        is Event.TooManyData -> Toast.makeText(requireContext(), event.text, Toast.LENGTH_SHORT).show()
        is Event.NoSearchData -> Toast.makeText(requireContext(), event.text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}