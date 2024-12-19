package com.example.recyclearn.Activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclearn.Adapter.TrashCollectionPlacesAdapter
import com.example.recyclearn.data.TrashCollectionPlace
import com.google.firebase.firestore.FirebaseFirestore

class TrashCollectionPlacesActivity : AppCompatActivity() {
    private lateinit var recyclerViewTrashPlaces: RecyclerView
    private lateinit var adapter: TrashCollectionPlacesAdapter
    private val places = mutableListOf<TrashCollectionPlace>()

    companion object {
        fun setupTrashCollectionPlaces(
            activity: AppCompatActivity,
            recyclerView: RecyclerView
        ) {
            val places = mutableListOf<TrashCollectionPlace>()
            val adapter = TrashCollectionPlacesAdapter(places)

            recyclerView.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerView.adapter = adapter

            val db = FirebaseFirestore.getInstance()
            db.collection("trashCollectionPlaces")
                .get()
                .addOnSuccessListener { querySnapshot ->
                    places.clear()
                    val fetchedPlaces = querySnapshot.toObjects(TrashCollectionPlace::class.java)
                    places.addAll(fetchedPlaces)
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(
                        activity,
                        "Failed to fetch places: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}