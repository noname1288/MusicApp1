# 🎵 Android Music Player (MVP + ExoPlayer)

This project is a **Music Player app** built with **Kotlin, XML layouts, MVP architecture, and ExoPlayer**.  
It can play audio files bundled in the app (`res/raw`), supports **background playback**, and allows users to control music from the **system UI (notification, lock screen, headset buttons)**.  

---

## ✨ Features

- 📂 **Handle audio data from `res/raw` folder**  
- ▶️ **Play / Pause / Seek** music using ExoPlayer  
- 🔊 **Background playback** using `ForegroundService`  
- 📱 **System UI integration**  
  - Media controls in notification  
  - Control from lock screen  
  - Headset button support  
- 🏗️ **MVP architecture**: clean separation of View, Presenter, and Model  

---

## 📂 Project Structure

```
app/
├── java/com/yourapp/musicplayer/
│   ├── model/
│   │   └── MusicModel.kt
│   │   └── MusicRepository.kt
│   │
│   ├── view/
│   │   ├── MainActivity.kt
│   │   ├── LocalFragment.kt
│   │   ├── InternetFragment.kt
│   │   └── PlayerFragment.kt
│   │
│   ├── presenter/
│   │   ├── LocalPresenter.kt
│   │   ├── InternetPresenter.kt
│   │   ├── PlayerPresenter.kt
│   │   └── contracts/
│   │       └── MusicContract.kt
│   │
│   ├── service/
│   │   ├── MusicPlayerService.kt
│   │   └── NotificationManager.kt
│   │
│   ├── adapter/
│   │   └── LocalAdapter.kt
│   │
│   └── utils/
│       └── MediaUtils.kt
│
├── res/
│   ├── layout/...
│   ├── raw/        # Store .mp3 files here
│   │   └── sample_music.mp3
│   └── drawable/...
```

---
## App Screenshoots
![Home Screen](assets/music1.jpeg)
![System UI](assets/music2.jpeg)
![Lock Screen](assets/music3.jpeg)
---

## 🚀 Next Steps

- Add online streaming support (InternetFragment)  
- Add playlist management  
- Show album art and metadata  
- Add seekbar + progress sync with system notification  
