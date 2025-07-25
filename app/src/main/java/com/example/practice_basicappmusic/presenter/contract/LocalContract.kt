package com.example.practice_basicappmusic.presenter.contract

import com.example.practice_basicappmusic.domain.MusicModel

interface LocalContract {
    interface View{
        fun showSongs(songs: List<MusicModel>)
    }

    interface LocalPresenter{
        fun loadLocalSongs()
        fun onSongSelected(song: MusicModel)
    }
}