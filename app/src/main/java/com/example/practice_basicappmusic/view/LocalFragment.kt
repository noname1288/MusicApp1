package com.example.practice_basicappmusic.view

import android.content.ComponentName
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_basicappmusic.adapter.LocalAdapter
import com.example.practice_basicappmusic.databinding.FragmentLocalBinding
import com.example.practice_basicappmusic.domain.MusicModel
import com.example.practice_basicappmusic.presenter.LocalPresenterImpl
import com.example.practice_basicappmusic.presenter.contract.LocalContract
import com.example.practice_basicappmusic.service.MyServiceMusic
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.get

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocalFragment : Fragment(), LocalContract.View {
    private var _binding: FragmentLocalBinding? = null
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private lateinit var sessionToken: SessionToken
    private lateinit var controllerFuture: ListenableFuture<MediaController>

    private lateinit var presenter: LocalContract.LocalPresenter
    private lateinit var adapter: LocalAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocalAdapter(emptyList()){ music ->
            presenter.onSongSelected(music)
        }

        recyclerView = binding.recyclerView
        binding.progressBar.visibility = View.VISIBLE
        presenter = LocalPresenterImpl(this, null)

        lifecycleScope.launch {
            delay(500)
            binding.progressBar.visibility = View.GONE
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)

            presenter.loadLocalSongs()
        }
    }

    override fun onStart() {
        super.onStart()
        initializeMediaController()
    }

    override fun onStop() {
        super.onStop()
        (presenter as? LocalPresenterImpl)?.releaseController()
        MediaController.releaseFuture(controllerFuture)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showSongs(songs: List<MusicModel>) {
        adapter.updateData(songs)
    }

    private fun initializeMediaController() {
        sessionToken = SessionToken(
            requireContext(),
            ComponentName(requireContext(), MyServiceMusic::class.java)
        )

        controllerFuture = MediaController.Builder(requireContext(), sessionToken)
            .buildAsync()

        controllerFuture.addListener({
            try {
                val controller = controllerFuture.get()
                (presenter as LocalPresenterImpl).setMediaController(controller)
                Log.d("LocalFragment", "MediaController connected")
            } catch (e: Exception) {
                Log.e("LocalFragment", "Error connecting to MediaController", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }


}