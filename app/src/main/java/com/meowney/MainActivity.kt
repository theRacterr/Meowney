package com.meowney

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.meowney.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // applying saved theme color
        val themePrefs = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        val themeOverlay = themePrefs.getInt("themeOverlay", 0)
        if (themeOverlay != 0) {
            theme.applyStyle(themeOverlay, true)
        }

        // applying saved night mode
        val sharedPref = getSharedPreferences("MeowneyPrefs", MODE_PRIVATE)
        val nightMode = sharedPref.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        AppCompatDelegate.setDefaultNightMode(nightMode)

        // inflating layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handling navbar
        // setupWithNavController is not used to eliminate the transition animation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val navOptions = NavOptions.Builder()
            .setEnterAnim(0)
            .setExitAnim(0)
            .setPopEnterAnim(0)
            .setPopExitAnim(0)
            .build()

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_entries -> {
                    navController.navigate(R.id.navigation_entries, null, navOptions)
                    true
                }
                R.id.navigation_stats -> {
                    navController.navigate(R.id.navigation_stats, null, navOptions)
                    true
                }
                R.id.navigation_more -> {
                    navController.navigate(R.id.navigation_more, null, navOptions)
                    true
                }
                else -> false
            }
        }

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
}