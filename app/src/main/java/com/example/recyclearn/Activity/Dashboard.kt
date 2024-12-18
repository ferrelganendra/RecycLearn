package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Artikel.HorizontalAdapter
import com.example.recyclearn.Artikel.Model
import com.example.recyclearn.R
import com.google.firebase.firestore.DocumentReference
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

        // Inisialisasi Firebase
        db = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        setupRecyclerView()

        // Setup Button Listeners
        setupButtonListeners()

        // Fetch Artikel Data
        fetchDataArtikelFromFirestore()
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

    private fun fetchDataArtikelFromFirestore() {
        db.collection("Artikel")
            .get()
            .addOnSuccessListener { result ->
                itemList.clear() // Bersihkan list sebelum menambah data baru
                for (document in result) {
                    val title = document.getString("judul") ?: "No Title"
                    val image = document.getString("gambar") ?: ""

                    // Log untuk debugging
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

