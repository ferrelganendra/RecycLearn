package com.example.recyclearn.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Artikel.Model
import com.example.recyclearn.Artikel.VerticalAdapter
import com.example.recyclearn.R
import com.google.firebase.firestore.FirebaseFirestore

class ListArticle : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VerticalAdapter
    private val itemList = mutableListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_article)

        // Inisialisasi Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Aktifkan tombol back
        supportActionBar?.title = "All Articles" // Judul Toolbar

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAll)
        recyclerView.layoutManager = LinearLayoutManager(this) // Orientasi vertikal
        adapter = VerticalAdapter(itemList)
        recyclerView.adapter = adapter

        fetchDataArtikelFromFirestore()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Navigasi kembali ke aktivitas sebelumnya
        return true
    }

    private fun fetchDataArtikelFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Artikel")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.getString("judul") ?: "No Title"
                    val image = document.getString("gambar") ?: ""
                    val desc = document.getString("deskripsi") ?: ""

                    itemList.add(Model(image, title, desc)) // Tambahkan data
                }
                adapter.notifyDataSetChanged() // Update adapter
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}