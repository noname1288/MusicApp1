package com.example.practice_basicappmusic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice_basicappmusic.R
import com.example.practice_basicappmusic.databinding.FragmentLocalBinding
import com.example.practice_basicappmusic.databinding.ItemMusicBinding
import com.example.practice_basicappmusic.domain.MusicModel

class LocalAdapter(
    private var songs: List<MusicModel>,
    private val onClick: (MusicModel) -> Unit
) : RecyclerView.Adapter<LocalAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(music: MusicModel){
            binding.tvTitle.text = music.title
            binding.tvArtist.text = music.artists

            // Set click listener for the item
            itemView.setOnClickListener {
                onClick(music)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LocalAdapter.ViewHolder {
        context = parent.context
        val binding = ItemMusicBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocalAdapter.ViewHolder, position: Int) {
        val song = songs[position]

        Glide.with(context)
            .load(R.drawable.outline_music_note_24)
            .into(holder.binding.imgAvt)

        holder.bind(song)


    }

    override fun getItemCount(): Int {
        return songs.size
    }

    fun updateData(newSongs: List<MusicModel>) {
        songs = newSongs
        notifyDataSetChanged()
    }

}