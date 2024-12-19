package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Artikel.HorizontalAdapter
import com.example.recyclearn.Artikel.Model
import com.example.recyclearn.R
import com.google.firebase.firestore.FirebaseFirestore

class Dashboard : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalAdapter
    private val itemList = mutableListOf<Model>()
    private lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        // Initialize Firebase
        db = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        setupRecyclerView()

        // Setup Button Listeners
        setupButtonListeners()

        // Setup Bottom Navigation Listeners
        setupBottomNavigation()

        // Fetch Artikel Data
        fetchDataArtikelFromFirestore()

        // Find the RecyclerView in your dashboard layout
        val recyclerViewTrashPlaces: RecyclerView = findViewById(R.id.recyclerViewTrashPlaces)

        // Setup Trash Collection Places
        TrashCollectionPlacesActivity.setupTrashCollectionPlaces(this, recyclerViewTrashPlaces)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter = HorizontalAdapter(itemList)
        recyclerView.adapter = adapter
    }

    private fun setupButtonListeners() {
        findViewById<Button>(R.id.OrgButton).setOnClickListener {
            startActivity(Intent(this, ListOrgActivity::class.java))
        }

        findViewById<Button>(R.id.AnorgButton).setOnClickListener {
            startActivity(Intent(this, ListAnorgActivity::class.java))
        }

        findViewById<Button>(R.id.B3button).setOnClickListener {
            startActivity(Intent(this, ListB3Activity::class.java))
        }
    }

    private fun setupBottomNavigation() {
        // Home button (current dashboard)
        findViewById<ImageButton>(R.id.homebut).setOnClickListener {
            // We're already in Dashboard, so no need to navigate
            Toast.makeText(this, "You are already on the Home page", Toast.LENGTH_SHORT).show()
        }

        // Course button
        findViewById<ImageButton>(R.id.Course).setOnClickListener {
            val intent = Intent(this, ListOrgActivity::class.java)
            startActivity(intent)
        }

        // Profile button
        findViewById<ImageButton>(R.id.profile).setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDataArtikelFromFirestore() {
        db.collection("Artikel")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear() // Clear list before adding new data
                for (document in result) {
                    val title = document.getString("judul") ?: "No Title"
                    val image = document.getString("gambar") ?: ""

                    // Log for debugging
                    Log.d("Dashboard", "Artikel: $title, Image: $image")

                    itemList.add(Model(image))
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("Dashboard", "Error fetching Artikel", exception)
            }
    }
}