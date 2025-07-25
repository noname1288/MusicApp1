package com.example.practice_basicappmusic.service


import android.content.Intent
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.practice_basicappmusic.R
import com.example.practice_basicappmusic.domain.MusicModel
import com.example.practice_basicappmusic.utils.UtilsApp

class MyServiceMusic : MediaSessionService() {
    private var mediaSession: MediaSession? = null
    private lateinit var player: ExoPlayer

    override fun onCreate() {
        super.onCreate()
        initializeSessionAndPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }

    private fun initializeSessionAndPlayer() {
        player = ExoPlayer.Builder(this).build().apply {
            addMediaItems(createPlaylist())
            prepare()
            playWhenReady = false
            addAnalyticsListener(EventLogger())
        }

        mediaSession = MediaSession.Builder(this, player).build()
    }

    private fun createPlaylist(): List<MediaItem> {
        val playlist = mutableListOf<MediaItem>()


        // Adding songs from raw resources
        addSongToPlaylist(playlist, R.raw.datan, "Da Tan", "Unknown Artist")
        addSongToPlaylist(playlist, R.raw.anhsaovabautroi, "Anh Sao Va Bau Troi", "Unknown Artist")
        addSongToPlaylist(playlist, R.raw.hoaimong, "Hoai Mong", "Unknown Artist")
        addSongToPlaylist(playlist, R.raw.noianhnghe, "Noi Anh Nghe", "Unknown Artist")

        return playlist

//        val playlist = MusicModel.mock_data.map { item -> UtilsApp.MusicModelToMediaItem(item) }.toMutableList()
//        return playlist
    }

    private fun addSongToPlaylist(playlist: MutableList<MediaItem>, resourceId: Int, title: String, artist: String) {
        val uri = Uri.parse("android.resource://${packageName}/${resourceId}")

        val mediaItem = MediaItem.Builder()
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

        playlist.add(mediaItem)
    }

}