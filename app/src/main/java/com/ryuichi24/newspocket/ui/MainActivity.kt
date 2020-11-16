package com.ryuichi24.newspocket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.ui.viewModels.MainViewModel
import com.ryuichi24.newspocket.utils.DependencyProvider

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // start setup
        setupViewModel()
        setupBottomNavigation()
    }

    // <----------------------------------------Setups---------------------------------------->
    private fun setupBottomNavigation() {
        val newsPocketFragmentContainer = findViewById<FragmentContainerView>(R.id.newsPocketFragmentContainer)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setupWithNavController(newsPocketFragmentContainer.findNavController())
    }

    private fun setupViewModel() {
        viewModel = DependencyProvider.provideViewModel(this)
    }
}