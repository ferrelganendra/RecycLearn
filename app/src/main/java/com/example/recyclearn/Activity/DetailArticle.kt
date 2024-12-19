package com.example.recyclearn.Activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.recyclearn.R

class DetailArticle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        // Inisialisasi Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Aktifkan tombol back
        supportActionBar?.title = "Detail Artikel" // Set judul Toolbar

        // Tangkap data dari intent
        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("deskripsi")
        val image = intent.getStringExtra("image")

        // Hubungkan dengan view
        val titleTextView: TextView = findViewById(R.id.detailTitle)
        val descTextView: TextView = findViewById(R.id.detailDesc)
        val imageView: ImageView = findViewById(R.id.detailImage)

        // Set data ke view
        titleTextView.text = judul
        descTextView.text = deskripsi
        Glide.with(this).load(image).into(imageView)
    }

    // Event tombol back di Toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}