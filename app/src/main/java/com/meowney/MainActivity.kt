package com.meowney

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.meowney.data.SettingsDataStore
import com.meowney.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsDataStore = SettingsDataStore(this)

        // applying saved theme color
        runBlocking {
            val themeOverlay = settingsDataStore.themeOverlay.first()
            if (themeOverlay != 0) {
                setTheme(themeOverlay)
            }
        }

        // applying saved night mode
        runBlocking {
            val nightMode = settingsDataStore.nightMode.first()
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }

        // inflating layout
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handling navbar
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bottomNavigationView = binding.navView
            when (destination.id) {
                R.id.navigation_entries -> bottomNavigationView.visibility = View.VISIBLE
                R.id.navigation_stats -> bottomNavigationView.visibility = View.VISIBLE
                R.id.navigation_more -> bottomNavigationView.visibility = View.VISIBLE
                else -> bottomNavigationView.visibility = View.GONE
            }
        }

        // handling fab
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fab = binding.fab
            when (destination.id) {
                R.id.navigation_entries -> {
                    fab.show()
                    fab.setOnClickListener {
                        navController.navigate(R.id.action_navigation_entries_to_addEntryFragment)
                    }
                }
                else -> fab.hide()
            }
        }
    }

    // hides app content from recents based on user preference
    override fun onPause() {
        super.onPause()

        runBlocking {
            val privacyMode = settingsDataStore.privacyMode.first()
            if (privacyMode) {
                window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
            } else {
                window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }
}