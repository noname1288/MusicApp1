package com.example.practice_basicappmusic.view

import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import androidx.media3.session.SessionToken
import com.example.practice_basicappmusic.R
import com.example.practice_basicappmusic.databinding.FragmentPlayerBinding
import com.example.practice_basicappmusic.presenter.PlayerPresenterImpl
import com.example.practice_basicappmusic.presenter.contract.PlayerContract
import com.example.practice_basicappmusic.service.MyServiceMusic
import com.google.common.util.concurrent.ListenableFuture

class PlayerFragment : Fragment(), PlayerContract.PlayerView {
    private lateinit var binding: FragmentPlayerBinding

    private lateinit var playerPresenter: PlayerContract.PLayerPresenter
    private lateinit var sessionToken : SessionToken
    private lateinit var controllerFuture: ListenableFuture<MediaController>
    private var mediaController: MediaController? = null
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        playerPresenter = PlayerPresenterImpl(this)
        initializeMediaController()

        binding.btnPlayPause.setOnClickListener {
            handlePlayPause()
        }

        binding.btnSkipNext.setOnClickListener {
            mediaController?.seekToNextMediaItem()
            playerPresenter.skipToNext()
        }

        binding.btnSkipPrevious.setOnClickListener {
            mediaController?.seekToPreviousMediaItem()
            playerPresenter.skipToPrevious()
        }
    }

    override fun onStart() {
        super.onStart()
        if (::controllerFuture.isInitialized && mediaController == null) {
            initializeMediaController()
        }
    }

    override fun onStop() {
        super.onStop()
        MediaController.releaseFuture(controllerFuture)
        mediaController = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::controllerFuture.isInitialized) {
            MediaController.releaseFuture(controllerFuture)
        }
        mediaController?.release()
    }

    private fun handlePlayPause() {
        mediaController?.let { controller ->
            isPlaying = !isPlaying
            when (isPlaying) {
                true -> {
                    binding.btnPlayPause.setImageResource(R.drawable.outline_pause_24)
                    controller.play()
                    playerPresenter.play()
                }
                false -> {
                    binding.btnPlayPause.setImageResource(R.drawable.baseline_play_arrow_24)
                    controller.pause()
                    playerPresenter.pause()
                }
            }
        }
    }

    private fun initializeMediaController() {
        sessionToken = SessionToken(
            requireContext(),
            ComponentName(requireContext(), MyServiceMusic::class.java)
        )

        controllerFuture = MediaController.Builder(requireContext(), sessionToken)
            .setListener(object : MediaController.Listener {
                override fun onCustomCommand(
                    controller: MediaController,
                    command: SessionCommand,
                    args: Bundle
                ): ListenableFuture<SessionResult> {
                    return super.onCustomCommand(controller, command, args)
                }

                override fun onDisconnected(controller: MediaController) {
                    super.onDisconnected(controller)
                    Log.d("PlayerFragment", "MediaController disconnected")
                }
            })
            .buildAsync()

        controllerFuture.addListener({
            try {
                mediaController = controllerFuture.get()
                Log.d("PlayerFragment", "MediaController initialized successfully")
                setupPlayerListener()
                updatePlayerUI()
            } catch (e: Exception) {
                Log.e("PlayerFragment", "Error initializing MediaController: ${e.message}")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun setupPlayerListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                updatePlayerUI()
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                this@PlayerFragment.isPlaying = isPlaying
                binding.btnPlayPause.setImageResource(
                    if (isPlaying) R.drawable.outline_pause_24
                    else R.drawable.baseline_play_arrow_24
                )
            }
        })
    }

    private fun updatePlayerUI() {
        mediaController?.let { controller ->
            val currentItem = controller.currentMediaItem
            currentItem?.mediaMetadata?.let { metadata ->
                binding.tvSongTitle.text = metadata.title ?: "Unknown Title"
                binding.tvArtistName.text = metadata.artist ?: "Unknown Artist"
            }
        }
    }

    override fun updateUI() {
        TODO("Not yet implemented")
    }


}
