package com.example.recyclearn.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Activity.VideoAdapter
import com.example.recyclearn.Activity.VideoPlayerActivity
import com.example.recyclearn.R
import com.example.recyclearn.data.VideoModel
import com.google.firebase.firestore.FirebaseFirestore

class CourseFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoAdapter: VideoAdapter
    private val videoList = mutableListOf<VideoModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course, container, false)


        recyclerView = view.findViewById(R.id.recyclerViewOrg)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        videoAdapter = VideoAdapter(videoList) { video ->
            // Open video when item is clicked
            openVideoPlayer(video)
        }
        recyclerView.adapter = videoAdapter

        fetchVideosFromFirestore()

        return view
    }

    private fun fetchVideosFromFirestore() {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("Organik")
            .get()
            .addOnSuccessListener { querySnapshot ->
                videoList.clear()
                for (document in querySnapshot.documents) {
                    val video = VideoModel(
                        documentId = document.id,
                        title = document.getString("title") ?: "Untitled Video",
                        thumbnailUrl = document.getString("thumbnailUrl") ?: "No Thumbnail",
                        videoUrl = document.getString("videoUrl") ?: "",
                        description = document.getString("description") ?: "No description available"
                    )
                    Log.d("Fiqri course", document.getString("title").toString())
                    videoList.add(video)
                }

                videoAdapter.notifyDataSetChanged()

                if (videoList.isEmpty()) {
                    Toast.makeText(requireContext(), "No Organik videos found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to load videos: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openVideoPlayer(video: VideoModel) {
        val intent = Intent(requireContext(), VideoPlayerActivity::class.java).apply {
            putExtra("VIDEO_URL", video.videoUrl)
            putExtra("VIDEO_TITLE", video.title)
            putExtra("VIDEO_DESCRIPTION", video.description)
        }
        startActivity(intent)
    }
}
