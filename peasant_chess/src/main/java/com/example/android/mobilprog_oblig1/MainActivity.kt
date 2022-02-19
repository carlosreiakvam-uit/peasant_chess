package com.example.android.mobilprog_oblig1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.android.mobilprog_oblig1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        // set toolbar
        setSupportActionBar(findViewById(R.id.toolbar))

        // set navHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        // set navController
        val navController = navHostFragment.navController

        // fjerner upbutton for startupFragment og resultsFragment
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.startupFragment, R.id.resultsFragment))

        // La toolbar ta i bruk navController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Fjerne titler fra toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

    }

    // Får tilbakefunksjon til å fungere
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}