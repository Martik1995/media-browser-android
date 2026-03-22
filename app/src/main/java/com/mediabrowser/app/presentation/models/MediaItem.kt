package com.mediabrowser.app.presentation.models

import androidx.recyclerview.widget.DiffUtil

data class MediaItem(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val releaseDate: String
) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MediaItem>() {
            override fun areItemsTheSame(
                oldItem: MediaItem,
                newItem: MediaItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MediaItem,
                newItem: MediaItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}