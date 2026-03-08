package com.example.twittersearchapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class TagAdapter(private val tags: MutableList<String>) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Ова сега е TextView бидејќи во новиот list_item.xml користиме текст за името
        val btnTag: TextView = view.findViewById(R.id.btnTagName)

        // Ова е MaterialButton за посовремен изглед
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.btnTag.text = tags[position]
    }

    override fun getItemCount(): Int {
        return tags.size
    }
}