package com.ardor.moviebroswer.view

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ardor.domain.model.SearchEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object CommonBindingAdapter {
    
    @BindingAdapter("imgResId")
    @JvmStatic
    fun setImageResourceFromNetwork(v: AppCompatImageView, url: String?) {
        Glide.with(v.context)
            .load(url)
            .apply(RequestOptions().centerCrop())
            .into(v)
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