package com.example.practice_basicappmusic.presenter.contract

interface PlayerContract {
    interface PlayerView{
        fun updateUI()
    }

    interface PLayerPresenter{
        fun updateUI()
        fun play()
        fun pause()
        fun skipToNext()
        fun skipToPrevious()
    }
}