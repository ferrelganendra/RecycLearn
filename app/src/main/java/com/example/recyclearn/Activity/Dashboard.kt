package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclearn.R
import com.google.firebase.firestore.FirebaseFirestore

class Dashboard : AppCompatActivity() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol untuk membuka halaman pertama
        val buttonPage1: Button = findViewById(R.id.OrgButton)
        buttonPage1.setOnClickListener {
            val intent = Intent(this, ListOrgActivity::class.java) // Ganti dengan nama Activity Anda
            startActivity(intent)
        }

        // Tombol untuk membuka halaman kedua
        val buttonPage2: Button = findViewById(R.id.AnorgButton)
        buttonPage2.setOnClickListener {
            val intent = Intent(this, ListAnorgActivity::class.java) // Ganti dengan nama Activity Anda
            startActivity(intent)
        }

        // Tombol untuk membuka halaman ketiga
        val buttonPage3: Button = findViewById(R.id.B3button)
        buttonPage3.setOnClickListener {
            val intent = Intent(this, ListB3Activity::class.java) // Ganti dengan nama Activity Anda
            startActivity(intent)
        }
    }
}