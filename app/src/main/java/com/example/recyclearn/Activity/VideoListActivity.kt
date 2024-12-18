package com.example.recyclearn.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.R
import com.example.recyclearn.data.VideoModel
import com.google.firebase.firestore.FirebaseFirestore

class VideoListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter
    private val videoList = mutableListOf<VideoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        recyclerView = findViewById(R.id.recyclerViewVideos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        videoAdapter = VideoAdapter(videoList) { video ->
            // Open video when item is clicked
            openVideoPlayer(video)
        }
        recyclerView.adapter = videoAdapter

        fetchVideosFromFirestore()
    }

    private fun fetchVideosFromFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Organik")
            .get()
            .addOnSuccessListener { querySnapshot ->
                videoList.clear()
                for (document in querySnapshot.documents) {
                    // Create VideoModel with description
                    val video = VideoModel(
                        documentId = document.id,
                        title = document.getString("title") ?: "Untitled Video",
                        thumbnailUrl = document.getString("thumbnailUrl") ?: "No Thumbnail",
                        videoUrl = document.getString("videoUrl") ?: "",
                        description = document.getString("description") ?: "No description available"
                    )
                    videoList.add(video)
                }

                videoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to load videos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openVideoPlayer(video: VideoModel) {
        val intent = Intent(this, VideoPlayerActivity::class.java).apply {
            putExtra("VIDEO_URL", video.videoUrl)
            putExtra("VIDEO_TITLE", video.title)
            putExtra("VIDEO_DESCRIPTION", video.description)
        }
        startActivity(intent)
    }
}