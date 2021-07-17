package com.example.zazor.ui.media.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.zazor.R
import com.example.zazor.data.models.Photo
import com.example.zazor.databinding.ItemMediaBinding
import com.example.zazor.utils.extensions.loadImage

class MediaListAdapter(private val photos: List<Photo>) : RecyclerView.Adapter<MediaListAdapter.MediaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaHolder =
        MediaHolder.newInstance(parent)

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class MediaHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {

            fun newInstance(parent: ViewGroup) =
                MediaHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false))
        }

        fun bind(photo: Photo) {
            itemView.run {
                findViewById<ImageView>(R.id.ivPreview).loadImage(photo.path, circle = false)
                findViewById<TextView>(R.id.tvLocation).text =
                    itemView.context.getString(R.string.location_pattern,
                    photo.lat.toString(), photo.lng.toString())
                findViewById<TextView>(R.id.tvAddress).text = photo.address
                findViewById<TextView>(R.id.tvDate).text = photo.date.toString("dd.MM.YYYY, HH:mm")
            }
        }
    }
}