package com.mediabrowser.app.presentation.mediaList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mediabrowser.app.databinding.ItemMediaBinding
import com.mediabrowser.app.presentation.models.MediaItem

class MediaListAdapter : ListAdapter<MediaItem, MediaListAdapter.MediaListVH>(MediaItem.DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListVH {
        return MediaListVH(
            binding = ItemMediaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediaListVH, position: Int) {
        holder.bind(model = getItem(position))
    }

    class MediaListVH(private val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MediaItem) = with(binding) {
            tvTitle.text = model.title
            tvReleaseDate.text = model.releaseDate
            tvDescription.text = model.description

            pbImage.isVisible = true

            ivPoster.load(model.imageUrl) {
                crossfade(true)
                listener(
                    onSuccess = { _, _ ->
                        pbImage.isVisible = false
                    },
                    onError = { _, _ ->
                        pbImage.isVisible = false
                    }
                )
            }
        }
    }
}