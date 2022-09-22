package com.ardor.moviebroswer.view

import androidx.activity.viewModels
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.base.BaseActivity
import com.ardor.moviebroswer.databinding.ActivityMainBinding
import com.ardor.moviebroswer.viewmodel.MovieViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MovieViewModel>(R.layout.activity_main) {

    override val viewModel: MovieViewModel by viewModels()

}