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

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter = HorizontalAdapter(itemList)
        recyclerView.adapter = adapter

        fetchDataArtikelFromFirestore()

//        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
//        db.collection("Artikel").get().addOnSuccessListener { result ->
//            for (document in result) {
//                Log.d("Fiqri>>>>>>>>>>>>>>>>>>>>>>", document.data.toString())
//            }
//        }.addOnFailureListener { exception ->
//            Log.e(">>>>>>>>>>>>>>>>>>>>>>Fiqri", "Error getting documents: ", exception)
//        }
//            Log.d(">>>>>>>>>>>>>>>>>>>>>Fiqri2", "123")
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        // Tombol untuk membuka halaman pertama
        val buttonPage1: Button = findViewById(R.id.OrgButton)
        buttonPage1.setOnClickListener {
            val intent =
                Intent(this, ListOrgActivity::class.java) // Ganti dengan nama Activity Anda
            startActivity(intent)
        }

        // Tombol untuk membuka halaman kedua
        val buttonPage2: Button = findViewById(R.id.AnorgButton)
        buttonPage2.setOnClickListener {
            val intent =
                Intent(this, ListAnorgActivity::class.java) // Ganti dengan nama Activity Anda
            startActivity(intent)
        }

        // Tombol untuk membuka halaman ketiga
        val buttonPage3: Button = findViewById(R.id.B3button)
        buttonPage3.setOnClickListener {
            val intent = Intent(this, ListB3Activity::class.java) // Ganti dengan nama Activity Anda
            startActivity(intent)
            val db = FirebaseFirestore.getInstance()

        }
    }

    private fun fetchDataArtikelFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("Artikel")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val title = document.getString("judul") ?: "No Title"
                    val image = document.getString("gambar") ?: ""
                    Log.d(">>>>>>>>>>>>>>>>>>>>>Fiqri2", image)
                    itemList.add(Model(image)) // Tambahkan ke list
                }
                adapter.notifyDataSetChanged() // Perbarui adapter
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error getting documents: ", exception)

            }
    }
}


