package com.example.recyclearn.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclearn.R
import com.example.recyclearn.data.TrashCollectionPlace

class TrashCollectionPlacesAdapter(
    private val places: List<TrashCollectionPlace>
) : RecyclerView.Adapter<TrashCollectionPlacesAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewTrashPlace)
        val nameTextView: TextView = itemView.findViewById(R.id.textViewPlaceName)
        val phoneTextView: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
//        val addressTextView: TextView = itemView.findViewById(R.id.textViewAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trash_collection_place, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = places[position]

        holder.nameTextView.text = place.name
        holder.phoneTextView.text = place.phoneNumber
//        holder.addressTextView.text = place.address

        // Load image using Glide
        Glide.with(holder.itemView.context)
            .load(place.imageUrl)
            .placeholder(R.drawable.profile)
            .into(holder.imageView)

        // Optional click listener
        holder.itemView.setOnClickListener {
            showPlaceDetails(holder.itemView.context, place)
        }
    }

    override fun getItemCount() = places.size

    private fun showPlaceDetails(context: Context, place: TrashCollectionPlace) {
        AlertDialog.Builder(context)
            .setTitle(place.name)
            .setMessage("""
                Phone: ${place.phoneNumber}
                Address: ${place.address}
                Materials: ${place.acceptedMaterials}
            """.trimIndent())
            .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}