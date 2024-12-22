package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclearn.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        // Initialize views
        videoView = findViewById(R.id.videoViewPlayer)
        titleTextView = findViewById(R.id.textViewVideoTitle)
        descriptionTextView = findViewById(R.id.textViewVideoDescription)

        // Retrieve video details from intent
        val videoUrl = intent.getStringExtra("VIDEO_URL")
        val videoTitle = intent.getStringExtra("VIDEO_TITLE")
        val videoDescription = intent.getStringExtra("VIDEO_DESCRIPTION")

        // Set title and description
        titleTextView.text = videoTitle
        descriptionTextView.text = videoDescription ?: "No description available"

        // Play video
//        videoUrl?.let { url ->
//            val mediaController = MediaController(this)
//            mediaController.setAnchorView(videoView)
//            videoView.setMediaController(mediaController)
//            videoView.setVideoURI(Uri.parse(url))
//            videoView.requestFocus()
//            videoView.start()
//        }
        val youtubePlayerView: YouTubePlayerView = findViewById(R.id.videoViewPlayer)
        lifecycle.addObserver(youtubePlayerView)

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "SRXH9AbT280" // Ganti dengan ID video YouTube yang ingin diputar
                youTubePlayer.loadVideo(videoId, 0f)
            }
          })
    }
}