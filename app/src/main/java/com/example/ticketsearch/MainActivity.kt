package com.example.ticketsearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.app.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val bundle = Bundle()
            when (item.itemId) {
                R.id.nav_hotels -> {
                    bundle.putString("screenName", getString(R.string.hotels))
                    navController.navigate(R.id.nav_hotels, bundle)
                }
                R.id.nav_koroche -> {
                    bundle.putString("screenName", getString(R.string.in_short))
                    navController.navigate(R.id.nav_koroche, bundle)
                }
                R.id.nav_subscriptions -> {
                    bundle.putString("screenName", getString(R.string.subscriptions))
                    navController.navigate(R.id.nav_subscriptions, bundle)
                }
                R.id.nav_profile -> {
                    bundle.putString("screenName", getString(R.string.profile))
                    navController.navigate(R.id.nav_profile, bundle)
                }
                else -> {
                    navController.navigate(item.itemId)
                }
            }
            true
        }
    }
}