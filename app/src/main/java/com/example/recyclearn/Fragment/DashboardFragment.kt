package com.example.recyclearn.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Activity.ListAnorgActivity
import com.example.recyclearn.Activity.ListArticle
import com.example.recyclearn.Activity.ListB3Activity
import com.example.recyclearn.Activity.ListOrgActivity
import com.example.recyclearn.Activity.TrashCollectionPlacesActivity
import com.example.recyclearn.Artikel.HorizontalAdapter
import com.example.recyclearn.Artikel.Model
import com.example.recyclearn.R
import com.google.firebase.firestore.FirebaseFirestore

class DashboardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalAdapter
    private val itemList = mutableListOf<Model>()
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Initialize Firebase
        db = FirebaseFirestore.getInstance()

        // Setup RecyclerView
        setupRecyclerView(view)

        // Setup Button Listeners
        setupButtonListeners(view)

        // Fetch Artikel Data
        fetchDataArtikelFromFirestore()

        // Find the RecyclerView in your fragment layout
        val recyclerViewTrashPlaces: RecyclerView = view.findViewById(R.id.recyclerViewTrashPlaces)

        // Setup Trash Collection Places
        TrashCollectionPlacesActivity.setupTrashCollectionPlaces(requireActivity(), recyclerViewTrashPlaces)
        val viewAllTextView: TextView = view.findViewById(R.id.textView11)
        viewAllTextView.setOnClickListener {
            val intent = Intent(requireContext(), ListArticle::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        adapter = HorizontalAdapter(itemList)
        recyclerView.adapter = adapter
    }

    private fun setupButtonListeners(view: View) {
        view.findViewById<Button>(R.id.OrgButton).setOnClickListener {
            startActivity(Intent(requireContext(), ListOrgActivity::class.java))
        }

        view.findViewById<Button>(R.id.AnorgButton).setOnClickListener {
            startActivity(Intent(requireContext(), ListAnorgActivity::class.java))
        }

        view.findViewById<Button>(R.id.B3button).setOnClickListener {
            startActivity(Intent(requireContext(), ListB3Activity::class.java))
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
                    val deskripsi = document.getString("deskripsi") ?: "asasas"

                    // Log for debugging
                    Log.d("DashboardFragment", "Artikel: $title, Image: $image")

                    itemList.add(Model(image,title,deskripsi))
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("DashboardFragment", "Error fetching Artikel", exception)
            }
    }
}
