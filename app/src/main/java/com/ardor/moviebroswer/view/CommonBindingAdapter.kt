package com.ardor.moviebroswer.view

import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.domain.model.SearchEntity
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
        value = ["data", "itemClickListener"],
        requireAll = false
    )
    @JvmStatic
    fun bindSearchResults(
        view: RecyclerView,
        data: List<SearchEntity>?,
        itemClickListener: MovieAdapter.ItemClickListener,
    ) {

        view.setHasFixedSize(true)
        view.addItemDecoration(
            DividerItemDecoration(
                view.context,
                LinearLayoutManager.VERTICAL
            )
        )
        data?.let { items ->
            val adapter = view.adapter as? MovieAdapter
            adapter?.submitList(items) ?: run {
                view.layoutManager = LinearLayoutManager(view.context)
                view.adapter = MovieAdapter(itemClickListener).apply {
                    submitList(items)
                }
            }
        }

    }

}