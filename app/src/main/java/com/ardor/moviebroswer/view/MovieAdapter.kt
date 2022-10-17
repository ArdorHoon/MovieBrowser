package com.ardor.moviebroswer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.domain.model.SearchEntity
import com.ardor.moviebroswer.databinding.MovieItemBinding
import com.bumptech.glide.Glide

class MovieAdapter(
    private val itemClickListener: ItemClickListener,
) : ListAdapter<SearchEntity, MovieAdapter.ViewHolder>(diffUtil) {

    interface ItemClickListener {
        fun moveDetailPage(result: SearchEntity)
    }

    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchEntity) {
            binding.title.text = item.title
            Glide.with(binding.root).load(item.poster).into(binding.mainImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchEntity>() {
            override fun areItemsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: SearchEntity, newItem: SearchEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

}