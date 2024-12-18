package com.example.recyclearn.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclearn.R
import com.example.recyclearn.data.VideoModel

class VideoAdapter(
    private val videoList: List<VideoModel>,
    private val onItemClick: (VideoModel) -> Unit
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videoList[position]
        holder.bind(video)
    }

    override fun getItemCount() = videoList.size

    class VideoViewHolder(
        itemView: View,
        private val onItemClick: (VideoModel) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val thumbnailImageView: ImageView = itemView.findViewById(R.id.imageViewThumbnail)
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)

        fun bind(video: VideoModel) {
            // Set title
            titleTextView.text = video.title

            // Load thumbnail using Glide (make sure to add Glide dependency)
            Glide.with(itemView.context)
                .load(video.thumbnailUrl)
//                .placeholder(R.drawable.placeholder_thumbnail) // Optional: add a placeholder image
//                .error(R.drawable.error_thumbnail) // Optional: add an error image
                .into(thumbnailImageView)

            // Set click listener
            itemView.setOnClickListener { onItemClick(video) }
        }
    }
}