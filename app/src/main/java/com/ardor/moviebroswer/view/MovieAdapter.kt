package com.ardor.moviebroswer.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardor.domain.model.SearchEntity
import com.ardor.moviebroswer.databinding.MovieItemBinding
import com.ardor.moviebroswer.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieAdapter(
    private val itemClickListener: ItemClickListener,
    private val viewModel: MovieViewModel
) : ListAdapter<SearchEntity, MovieAdapter.ViewHolder>(diffUtil) {

    interface ItemClickListener {
        fun moveDetailPage(result: SearchEntity)
    }

    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchEntity) {
            if (adapterPosition == 0 || adapterPosition == currentList.size.minus(1)) {
                binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                val screenWidth = binding.root.context.resources.displayMetrics.widthPixels
                val mLayoutParam: RecyclerView.LayoutParams =
                    binding.root.layoutParams as RecyclerView.LayoutParams
                if (adapterPosition == 0)
                    mLayoutParam.leftMargin = 200
                else
                    mLayoutParam.rightMargin = 200
                        //(screenWidth - binding.root.measuredWidthAndState) / 3
            }

            Glide.with(binding.root).load(item.poster).into(binding.mainImg)
            binding.root.setOnClickListener {
                itemClickListener.moveDetailPage(item)
            }

            //fix it
            CoroutineScope(Dispatchers.IO).launch {
                binding.favoriteBtn.isChecked = viewModel.checkCurrentFavorite(item.imdbID)
            }

            binding.favoriteBtn.setOnClickListener {
                if (binding.favoriteBtn.isChecked) {
                    viewModel.insertFavorite(item)
                } else {
                    viewModel.deleteFavorite(item.imdbID)
                }
            }
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