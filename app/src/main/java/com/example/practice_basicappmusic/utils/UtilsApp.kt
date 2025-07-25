package com.example.practice_basicappmusic.utils

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.practice_basicappmusic.domain.MusicModel

object UtilsApp {
    fun MusicModelToMediaItem(musicModel: MusicModel) : MediaItem{
        val packageName = "com.example.practice_basicappmusic"
        val title = musicModel.title
        val artist = musicModel.artists
        val resourceId = musicModel.link_mp3

        val uri = Uri.parse("android.resource://${packageName}/${resourceId}")

        return MediaItem.Builder()
            .setUri(uri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(artist)
                    .setIsBrowsable(false)
                    .setIsPlayable(true)
                    .build()
            )
            .build()
    }
}