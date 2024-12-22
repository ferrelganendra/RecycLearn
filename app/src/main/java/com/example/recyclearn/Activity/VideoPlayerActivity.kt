package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclearn.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var youtubePlayerView: YouTubePlayerView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        // Initialize views
        youtubePlayerView = findViewById(R.id.videoViewPlayer)
        titleTextView = findViewById(R.id.textViewVideoTitle)
        descriptionTextView = findViewById(R.id.textViewVideoDescription)

        // Retrieve video details from intent
        val videoTitle = intent.getStringExtra("VIDEO_TITLE") ?: "No title"
        val videoDescription = intent.getStringExtra("VIDEO_DESCRIPTION") ?: "No description available"

        // Set title and description
        titleTextView.text = videoTitle
        descriptionTextView.text = videoDescription

        // Observe lifecycle and initialize YouTube player
        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = intent.getStringExtra("VIDEO_ID") ?: "SRXH9AbT280" // Default video ID
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }
}
