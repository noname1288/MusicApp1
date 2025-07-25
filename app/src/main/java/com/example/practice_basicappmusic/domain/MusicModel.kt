package com.example.practice_basicappmusic.domain

import android.provider.MediaStore

data class MusicModel(
    val id: Int = 0,
    val title: String = "",
    val artists: String = "",
    val link_mp3: String = ""
){
    companion object{
        val mock_data = listOf<MusicModel>(
            MusicModel(0, "Datan", "Unknow Artist", "R.raw.datan"),
            MusicModel(1, "Anh Sao Va Bau Troi", "Unknow Artist", "R.raw.anhsaovabautroi"),
            MusicModel(2, "Hoai Mong", "Unknow Artist", "R.raw.hoaimong"),
            MusicModel(3, "Noi Anh Nghe", "Unknow Artist", "R.raw.noianhnghe"),
        )
    }
}
