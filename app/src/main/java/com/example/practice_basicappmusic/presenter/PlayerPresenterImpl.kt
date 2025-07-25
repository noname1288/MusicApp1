package com.example.practice_basicappmusic.presenter

import android.util.Log
import android.widget.MediaController
import com.example.practice_basicappmusic.presenter.contract.PlayerContract

class PlayerPresenterImpl (
    private val view: PlayerContract.PlayerView,
    private var mediaController: MediaController ? = null
) :
PlayerContract.PLayerPresenter{
    override fun updateUI() {
        TODO("Not yet implemented")
    }

    override fun play() {
        Log.d("PlayerPresenterImpl", "Playing")
    }

    override fun pause() {
        Log.d("PlayerPresenterImpl", "Paused")
    }

    override fun skipToNext() {
        Log.d("PlayerPresenterImpl", "Skipping to next song")
    }

    override fun skipToPrevious() {
        Log.d("PlayerPresenterImpl", "previous song")
    }

}