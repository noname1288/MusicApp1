package com.example.practice_basicappmusic.presenter

import android.util.Log
import androidx.media3.session.MediaController
import com.example.practice_basicappmusic.domain.MusicModel
import com.example.practice_basicappmusic.presenter.contract.LocalContract

class LocalPresenterImpl (
    private val view: LocalContract.View,
    private var mediaController: MediaController ?= null
) : LocalContract.LocalPresenter {

    override fun loadLocalSongs() {
        try {
            val songs = MusicModel.mock_data
            view.showSongs(songs)
        } catch (e: Exception) {

        }
    }

    override fun onSongSelected(song: MusicModel) {
        val id = song.id
        mediaController?.let { controller ->
            controller.seekToDefaultPosition(id)
            controller.play()
            Log.d("LocalPresenter", "Playing song: ${song.title}")

        }

        // You could start service or notify PlayerFragment here
        Log.d("LocalPresenter", "Selected: ${song.title}")
    }

    fun setMediaController(controller: MediaController) {
        mediaController = controller
    }

    fun releaseController() {
        mediaController = null
    }


}