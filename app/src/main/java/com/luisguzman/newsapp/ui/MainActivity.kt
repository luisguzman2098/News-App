package com.luisguzman.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.luisguzman.newsapp.R
import com.luisguzman.newsapp.databinding.ActivityMainBinding
import com.luisguzman.newsapp.utils.hideVisibility
import com.luisguzman.newsapp.utils.showVisibility

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        observeDestinationChanged()
    }

    private fun observeDestinationChanged() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.webviewFragment -> {
                    binding.appBarLayout.hideVisibility()
                    binding.bottomNavigation.hideVisibility() }
                else -> {
                    binding.appBarLayout.showVisibility()
                    binding.bottomNavigation.showVisibility()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragmentContainer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}