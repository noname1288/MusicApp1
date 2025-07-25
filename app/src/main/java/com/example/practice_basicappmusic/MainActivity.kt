package com.example.practice_basicappmusic

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practice_basicappmusic.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.example.practice_basicappmusic.view.InternetFragment
import com.example.practice_basicappmusic.view.LocalFragment
import com.example.practice_basicappmusic.view.PlayerFragment

class MainActivity : AppCompatActivity() {
    private lateinit var connMgr: ConnectivityManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if network is available
        if (isNetworkAvailable(this)){
            Toast.makeText(this, "Network is available", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_SHORT).show()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up activity
        setupView()
    }

    private fun setupView() {
        replaceFragment(InternetFragment())
        showPlayer(PlayerFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_internet_storage -> {
                    replaceFragment(InternetFragment())
                }

                R.id.nav_local_storage -> {
                    replaceFragment(LocalFragment())
                }
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

    private fun showPlayer(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frg_player, fragment)
        fragmentTransaction.commit()
    }

    // Check if network is available
    private fun isNetworkAvailable(context: Context): Boolean {
        connMgr = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        //For Android Q (API >= 29)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capability =
                connMgr.getNetworkCapabilities(connMgr.activeNetwork)
            return capability != null &&
                    (capability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capability.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
        } else {
            @Suppress("DEPRECATION")
            val activeNetworkInfo = connMgr.activeNetworkInfo
            @Suppress("DEPRECATION")
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}