package com.ardor.moviebroswer.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.base.BaseFragment
import com.ardor.moviebroswer.databinding.FragmentHomeBinding
import com.ardor.moviebroswer.viewmodel.MovieViewModel


class HomeFragment() :
    BaseFragment<FragmentHomeBinding, MovieViewModel>(R.layout.fragment_home) {

    override val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}