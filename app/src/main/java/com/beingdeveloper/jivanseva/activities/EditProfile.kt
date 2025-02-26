package com.beingdeveloper.jivanseva.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.beingdeveloper.jivanseva.R
import com.beingdeveloper.jivanseva.databinding.ActivityEditProfileBinding
import com.beingdeveloper.updateprofile.HomeFragment

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup the toolbar
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false) // Initially, no back button

        // Load the HomeFragment initially
        if (savedInstanceState == null) {
            navigateToFragment(HomeFragment(), false)
        }

        // Toolbar back button functionality
        binding.materialToolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        // Add a fragment listener to monitor fragment changes
        supportFragmentManager.addOnBackStackChangedListener {
            updateBackButtonVisibility()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    // Navigate to a new fragment
    fun navigateToFragment(fragment: Fragment, showBackButton: Boolean) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            if (showBackButton) {
                addToBackStack(null)
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    }

    // Update the back button visibility based on the current fragment
    private fun updateBackButtonVisibility() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment is HomeFragment) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false) // Hide back button
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show back button
        }
    }
}