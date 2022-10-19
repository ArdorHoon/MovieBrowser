package com.ardor.moviebroswer.view

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ardor.domain.model.SearchEntity
import com.ardor.moviebroswer.R
import com.ardor.moviebroswer.core.extension.ScaleLayoutManager
import com.ardor.moviebroswer.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object CommonBindingAdapter {

    @BindingAdapter("setOnQueryTextListener")
    @JvmStatic
    fun setOnQueryTextListener(view : SearchView, searchMovie: (String?) -> Unit){
        view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchMovie(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    @BindingAdapter("checkFavorite")
    @JvmStatic
    fun checkFavorite(view : AppCompatCheckBox, flag : Boolean) {
        view.isChecked = flag
    }

    @BindingAdapter("imgResId")
    @JvmStatic
    fun setImageResourceFromNetwork(view: AppCompatImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(view)
    }

    @BindingAdapter(
        value = ["data", "itemClickListener", "viewModel"],
        requireAll = false
    )
    @JvmStatic
    fun bindSearchResults(
        view: RecyclerView,
        data: List<SearchEntity>?,
        itemClickListener: MovieAdapter.ItemClickListener,
        viewModel: MovieViewModel
    ) {
        val snap = PagerSnapHelper()

        view.setHasFixedSize(true)
        view.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )

        if(view.onFlingListener == null) {
            snap.attachToRecyclerView(view)
        }

        data?.let { items ->
            val adapter = view.adapter as? MovieAdapter
            adapter?.submitList(items) ?: run {
//                view.layoutManager = ScaleLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                view.adapter = MovieAdapter(itemClickListener, viewModel).apply {
                    submitList(items)
                }
            }
        }

        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var currentPosition = RecyclerView.NO_POSITION
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView.layoutManager != null) {
                    val snapView = snap.findSnapView(recyclerView.layoutManager)!!
                    val position = recyclerView.layoutManager!!.getPosition(snapView)

                    if (currentPosition != position) {
                        currentPosition = position
                        if(view.id == R.id.movies) {
                            viewModel.setTitleAndMovie(currentPosition)
                        }else{
                            viewModel.setFavoriteTitleAndMovie(currentPosition)
                        }
                    }
                }
            }
        })
    }

}