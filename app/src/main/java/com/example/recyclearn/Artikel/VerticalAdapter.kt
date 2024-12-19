package com.example.recyclearn.Artikel

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclearn.Activity.DetailArticle
import com.example.recyclearn.R

class VerticalAdapter(private val itemList: List<Model>) :
    RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        val itemTitle: TextView = view.findViewById(R.id.judul)
        val itemDesc: TextView = view.findViewById(R.id.deskripsi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artikel_vertical, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemTitle.text = item.judul
        holder.itemDesc.text = item.deskripsi
        Glide.with(holder.itemView.context).load(item.image).into(holder.itemImage)

        // Tambahkan klik listener
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailArticle::class.java).apply {
                putExtra("judul", item.judul)
                putExtra("deskripsi", item.deskripsi)
                putExtra("image", item.image)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = itemList.size
}