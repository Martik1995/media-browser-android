package com.mediabrowser.app.presentation.mediaList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mediabrowser.app.R
import com.mediabrowser.app.databinding.ItemMediaBinding
import com.mediabrowser.app.presentation.models.MediaItem

class MediaListAdapter(
    private val onItemClick: (MediaItem) -> Unit
) : ListAdapter<MediaItem, MediaListAdapter.MediaListVH>(MediaItem.DIFF_UTIL) {
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

    inner class MediaListVH(private val binding: ItemMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MediaItem) = with(binding) {

            root.setOnClickListener {
                onItemClick(model)
            }

            tvTitle.text = model.title
            tvReleaseDate.text = model.releaseDate
            tvDescription.text = model.description

            pbImage.isVisible = true

            ivPoster.load(model.imageUrl) {
                crossfade(true)
                error(R.drawable.ic_error_outline)
                listener(
                    onSuccess = { _, _ -> pbImage.isVisible = false },
                    onError = { _, _ -> pbImage.isVisible = false }
                )
            }
        }
    }
}